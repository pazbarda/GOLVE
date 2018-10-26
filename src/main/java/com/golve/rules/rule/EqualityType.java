package com.golve.rules.rule;

public enum EqualityType {
	GREATER, //
	LESS, //
	EQUALS; //
//	UNKNOWN; //
	
	public boolean evaluate(int value, int reference) {
		switch (this) {
		case GREATER:
			return (value>reference);
		case LESS:
			return (value<reference);
		case EQUALS:
			return (value==reference);
//		case UNKNOWN:
		default:
			return false;
		}
	}
}
