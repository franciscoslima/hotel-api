package com.capgemini.hotelapi.exceptions;

import com.capgemini.hotelapi.model.QuartoStatus;

public class InvalidRoomStatusException  extends RuntimeException{
    public InvalidRoomStatusException(String message) {
        super(message);
    }

    public InvalidRoomStatusException(String message, QuartoStatus status) {
        super(message + "Status atual do quarto: " + status.getNome() + ".");
    }
}
