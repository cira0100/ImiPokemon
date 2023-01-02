package models;

import java.io.Serializable;

public enum GameStatus implements Serializable {
	WAITING_FOR_SECOND_PLAYER,
	PLAYING,
	PLAYER1WIN,
	PLAYER2WIN,
	NOT_IN_GAME,
	RECEIVING_GAME_REQUEST
}
