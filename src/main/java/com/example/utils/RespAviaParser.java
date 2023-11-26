package com.example.utils;

import com.example.dto.avia.AviaDto;
import com.example.models.SubjectType;
import com.example.request.models.aviasales.AviaRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RespAviaParser {
    @Getter
    private JsonNode response;
    JsonMapper mapper = new JsonMapper();

    private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    public RespAviaParser(JsonNode response){
        this.response = response;
    }
    public List<AviaDto> getInfo(AviaRequest request){
        ArrayNode responseFinal = mapper.createObjectNode().putArray("jsonNodeList");
        Map<Integer, Integer> numberMap = new HashMap<>();

        int i = 0;
        for (JsonNode offerItem : response.get("payload").get("offers")) {
            if (flagsCheck(request, offerItem)) {
                for (JsonNode offerSegmentItems : offerItem.get("segmentsInfo")) {
                    for (JsonNode offerSegmentItem : offerSegmentItems) {
                        if (request.getIsHandBaggage().contains(offerSegmentItem.get("handBaggage").asBoolean())) {
                            setNotDataParams(responseFinal, offerItem, offerSegmentItem);
                            for (JsonNode flightNumbers : offerItem.get("flights")){
                                numberMap.put(Integer.parseInt(flightNumbers.toString()), i);
                                i++;
                            }
                        }
                    }
                }
            }
        }

        i = 0;
        if (!responseFinal.isEmpty()) {
            for (JsonNode flighItem : response.get("payload").get("flights")) {
                for (JsonNode flightSegment : flighItem.get("flightSegments")) {
                    String numberFlight = flightSegment.get("number").asText();
                    if (numberMap.containsKey(i)) {
                        getFlightSegments("departure", flightSegment, responseFinal, numberMap.get(i));
                        getFlightSegments("arrival", flightSegment, responseFinal, numberMap.get(i));
                    }
                    else{
                        System.out.println(numberFlight);
                    }
                    i++;
                }
            }
        }

        if (!responseFinal.isEmpty()) {
            for (JsonNode jsonNode : responseFinal){
                setInfo(jsonNode, response.get("payload").get("info"));
            }
        }

        List<AviaDto> aviaDtoList = new ArrayList<>();
        for (JsonNode node : responseFinal) {
            AviaDto aviaDto = new AviaDto();
            aviaDto.setGroupId(node.get("groupId").asText());
            aviaDto.setPrice(node.get("price").asDouble());
            aviaDto.setCharter(node.get("isCharter").asBoolean());
            aviaDto.setCarrier(node.get("validatingCarrier").asText());
            aviaDto.setWithBaggage(node.get("withBaggage").asBoolean());
            aviaDto.setWithSkiEquipment(node.get("withSkiEquipment").asBoolean());

            aviaDto.setDepartureDateTime(LocalDateTime.parse(node.get("departure_time").asText(), formatter));
            aviaDto.setDepartureAirport(node.get("departure_airport").asText());
            aviaDto.setDepartureTerminal(node.get("departure_terminal").asText());
            aviaDto.setDepartureCity(node.get("departure_city").asText());

            aviaDto.setArrivalDateTime(LocalDateTime.parse(node.get("arrival_time").asText(), formatter));
            aviaDto.setArrivalAirport(node.get("arrival_airport").asText());
            aviaDto.setArrivalTerminal(node.get("arrival_terminal").asText());
            aviaDto.setArrivalCity(node.get("arrival_city").asText());
            aviaDto.setOrder(request.getOrder());
            aviaDtoList.add(aviaDto);
        }
        System.out.println(aviaDtoList);
        return aviaDtoList;
    }

    private void setNotDataParams(ArrayNode responseFinal,
                                  JsonNode offerItem,
                                  JsonNode offerSegmentItem){
        ObjectNode jsonNodes = mapper.createObjectNode();
        jsonNodes.set("groupId", offerItem.get("groupId"));

        boolean handBaggageFlag = !offerSegmentItem.get("handBaggage").isEmpty();
        jsonNodes.set("isHandBaggage", mapper.createObjectNode().booleanNode(handBaggageFlag));

        jsonNodes.set("price", offerItem.get("price").get("amount"));
        jsonNodes.set("isCharter", offerItem.get("isCharter"));
        jsonNodes.set("validatingCarrier", offerItem.get("validatingCarrier"));
        jsonNodes.set("withBaggage", offerItem.get("withBaggage"));
        jsonNodes.set("withSkiEquipment", offerItem.get("withSkiEquipment"));
        responseFinal.add(jsonNodes);
    }

    private boolean flagsCheck(AviaRequest request, JsonNode offerItem){
        return request.getIsCharter().contains(offerItem.get("isCharter").asBoolean())
                && request.getIsBaggage().contains(offerItem.get("withBaggage").asBoolean())
                && request.getIsSkiEquipment().contains(offerItem.get("withSkiEquipment").asBoolean())
                &&  offerItem.get("price").get("amount").asDouble() <= request.getMaxPrice();
    }

    private void getFlightSegments(String type,
                               JsonNode offerItem,
                               ArrayNode responseFinal,
                               Integer numberFlight){
        JsonNode departureJsone = offerItem.get(type);
        ObjectNode jsonNode = (ObjectNode) responseFinal.get(numberFlight);
        jsonNode.set(type + "_time", departureJsone.get("time"));
        jsonNode.set(type + "_airport", departureJsone.get("airport"));
        jsonNode.set(type + "_terminal", departureJsone.get("terminal"));
        jsonNode.set(type + "_city", departureJsone.get("city"));

        responseFinal.remove(numberFlight);
        responseFinal.insert(numberFlight, jsonNode);
    }

    private void setInfo(JsonNode jsonNode, JsonNode infoItem){
        ((ObjectNode) jsonNode).set("departure_airport" , infoItem.get("airportNames").get(jsonNode.get("departure_airport").asText()));
        ((ObjectNode) jsonNode).set("arrival_airport" , infoItem.get("airportNames").get(jsonNode.get("arrival_airport").asText()));
        ((ObjectNode) jsonNode).set("validatingCarrier" , infoItem.get("carrierNames").get(jsonNode.get("validatingCarrier").asText()));
        ((ObjectNode) jsonNode).set("departure_city" , infoItem.get("cities").get(jsonNode.get("departure_city").asText()).get("name"));
        ((ObjectNode) jsonNode).set("arrival_city" , infoItem.get("cities").get(jsonNode.get("arrival_city").asText()).get("name"));
    }

    public void getSubjectType(JsonNode response){
        SubjectType subjectType = new SubjectType();
        for (JsonNode resp : response){
            subjectType.setName(resp.get("validatingCarrier").asText());
        }
    }
}
