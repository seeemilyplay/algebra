package seeemilyplay.algebra.sequences.jigsaw;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seeemilyplay.quizzer.cream.IntegerConstraints;
import seeemilyplay.quizzer.cream.IntegerRange;
import seeemilyplay.quizzer.cream.SingleInteger;

final class VariableLineParser {

	private static final int FIRST_INT_GROUP_INDEX = 1;
	private static final int SECOND_INT_GROUP_INDEX = 2;
	
	private static final String REGEX_BODY = "=(-?\\d)+(?:->((-?\\d+)))?";
	
	private final String description;
	
	public VariableLineParser(String description) {
		this.description = description;
	}
	
	public IntegerConstraints parseVariableConstraints(
			String variableName, 
			IntegerConstraints defaultConstraints) {
		if (!exists(variableName)) {
			return defaultConstraints;
		}
		return parseVariableConstraints(variableName);
	}
	
	public IntegerConstraints parseVariableConstraints(String variableName) {
		String regex = createRegex(variableName);
		Matcher matcher = createMatcher(regex);
		if (isSingleIntegerConstraint(matcher)) {
			return parseSingleIntegerConstraint(matcher);
		} else {
			return parseIntegerRangeConstraint(matcher);
		}
	}
	
	private boolean exists(String variableName) {
		return description.contains(variableName);
	}
	
	private String createRegex(String variableName) {
		StringBuilder sb = new StringBuilder();
		sb.append(variableName);
		sb.append(REGEX_BODY);
		return sb.toString();
	}
	
	private Matcher createMatcher(String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(description);
		matcher.find();
		return matcher;
	}
	
	private boolean isSingleIntegerConstraint(Matcher matcher) {
		return ((matcher.groupCount()<=SECOND_INT_GROUP_INDEX) 
				|| (matcher.group(SECOND_INT_GROUP_INDEX)==null));
	}
	
	private IntegerConstraints parseSingleIntegerConstraint(Matcher matcher) {
		return new SingleInteger(
				parseFirstInt(matcher));
	}
	
	private IntegerConstraints parseIntegerRangeConstraint(Matcher matcher) {
		int a = parseFirstInt(matcher);
		int b = parseSecondInt(matcher);
		return new IntegerRange(
				Math.min(a, b),
				Math.max(a, b));
	}
	
	private int parseFirstInt(Matcher matcher) {
		return parseGroupAsInt(matcher, FIRST_INT_GROUP_INDEX);
	}
	
	private int parseSecondInt(Matcher matcher) {
		return parseGroupAsInt(matcher, SECOND_INT_GROUP_INDEX);
	}
	
	private int parseGroupAsInt(Matcher matcher, int groupIndex) {
		String group = matcher.group(groupIndex);
		return Integer.parseInt(group);
	}
}
