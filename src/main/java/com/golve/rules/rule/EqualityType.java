package com.golve.rules.rule;

import java.text.ParseException;

/**
 * enum representation of equality sign. useful for rules and conditions.
 * @author pazb
 *
 */
public enum EqualityType {
	GREATER(">"), //
	LESS("<"), //
	EQUALS("="); //
	
	private final String stringValue;
	
	private EqualityType(String stringValue) {
		this.stringValue = stringValue;
	}

	public boolean evaluate(int value, int reference) {
		switch (this) {
		case GREATER:
			return (value>reference);
		case LESS:
			return (value<reference);
		case EQUALS:
			return (value==reference);
		default:
			return false;
		}
	}
	
	public String toString() {
		return stringValue;
	}
	
	public static EqualityType fromString(String str) throws ParseException {
		for (EqualityType type : EqualityType.values()) {
			if (str.equals(type.toString())) {
				return type;
			}
		}
		throw new ParseException(str + " does not correspond to a valid equality type", 0);
	}
}
