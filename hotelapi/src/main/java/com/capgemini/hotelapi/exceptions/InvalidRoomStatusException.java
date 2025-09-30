package com.capgemini.hotelapi.exceptions;

import com.capgemini.hotelapi.model.QuartoStatusEnum;

public class InvalidRoomStatusException  extends RuntimeException{
    public InvalidRoomStatusException(String message) {
        super(message);
    }

    public InvalidRoomStatusException(String message, QuartoStatusEnum status) {
        super(message + "Status atual do quarto: " + status.getNome() + ".");
    }
}
