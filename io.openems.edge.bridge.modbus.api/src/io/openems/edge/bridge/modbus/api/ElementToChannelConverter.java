package io.openems.edge.bridge.modbus.api;

import java.util.function.Function;

/**
 * Provides Functions to convert from Element to Channel and back. Also has some
 * static convenience functions to facilitate conversion.
 */
public class ElementToChannelConverter {

	/**
	 * Converts directly 1-to-1 between Element and Channel
	 */
	public final static ElementToChannelConverter DIRECT_1_TO_1 = new ElementToChannelConverter( //
			// element -> channel
			value -> value, //
			// channel -> element
			value -> value);

	/**
	 * Converts between Element 'Float' to Channel 'Integer'
	 */
	public final static ElementToChannelConverter FLOAT_TO_INT = new ElementToChannelConverter( //
			// element -> channel
			value -> ((Float) value).intValue(), //
			// channel -> element
			value -> ((Integer) value).floatValue());

	/**
	 * Applies a scale factor of 2.
	 * 
	 * @see ElementToChannelScaleFactorConverter
	 */
	public final static ElementToChannelConverter SCALE_FACTOR_2 = new ElementToChannelScaleFactorConverter(2);

	/**
	 * Applies a scale factor of 3.
	 * 
	 * @see ElementToChannelScaleFactorConverter
	 */
	public final static ElementToChannelConverter SCALE_FACTOR_3 = new ElementToChannelScaleFactorConverter(3);

	/**
	 * Converts only positive values from Element to Channel
	 */
	public final static ElementToChannelConverter CONVERT_POSITIVE = new ElementToChannelConverter( //
			// element -> channel
			value -> {
				if (value == null) {
					return null;
				}
				if (!(value instanceof Integer)) {
					throw new IllegalArgumentException("CONVERT_POSITIVE accepts only Integer type");
				}
				int intValue = (int) value;
				if (intValue >= 0) {
					return intValue;
				} else {
					return 0;
				}
			}, //
				// channel -> element
			value -> value);

	/**
	 * Converts only negative values from Element to Channel and inverts them (makes
	 * the value positive)
	 */
	public final static ElementToChannelConverter CONVERT_NEGATIVE_INVERT = new ElementToChannelConverter( //
			// element -> channel
			value -> {
				if (value == null) {
					return null;
				}
				if (!(value instanceof Integer)) {
					throw new IllegalArgumentException("CONVERT_NEGATIVE_AND_INVERT accepts only Integer type");
				}
				int intValue = (int) value;
				if (intValue >= 0) {
					return 0;
				} else {
					return intValue * -1;
				}
			}, //
				// channel -> element
			value -> value);

	/**
	 * Applies SCALE_FACTOR_2 and CONVERT_POSITIVE
	 */
	public final static ElementToChannelConverter SCALE_FACTOR_2_AND_CONVERT_POSITIVE = new ElementToChannelConverterChain(
			SCALE_FACTOR_2, CONVERT_POSITIVE);

	/**
	 * Applies SCALE_FACTOR_2 and CONVERT_NEGATIVE_AND_INVERT
	 */
	public final static ElementToChannelConverter SCALE_FACTOR_2_AND_CONVERT_NEGATIVE_INVERT = new ElementToChannelConverterChain(
			SCALE_FACTOR_2, CONVERT_NEGATIVE_INVERT);

	/**
	 * Applies SCALE_FACTOR_3 and FLOAT_TO_INT
	 */
	public final static ElementToChannelConverter FLOAT_TO_INT_AND_SCALE_FACTOR_3 = new ElementToChannelConverterChain(
			FLOAT_TO_INT, SCALE_FACTOR_3);

	/**
	 * Applies FLOAT_TO_INT and CONVERT_POSITIVE
	 */
	public final static ElementToChannelConverter FLOAT_TO_INT_AND_CONVERT_POSITIVE = new ElementToChannelConverterChain(
			FLOAT_TO_INT, CONVERT_POSITIVE);

	/**
	 * Applies FLOAT_TO_INT and CONVERT_NEGATIVE_INVERT
	 */
	public final static ElementToChannelConverter FLOAT_TO_INT_AND_CONVERT_NEGATIVE_INVERT = new ElementToChannelConverterChain(
			FLOAT_TO_INT, CONVERT_NEGATIVE_INVERT);

	private final Function<Object, Object> elementToChannel;
	private final Function<Object, Object> channelToElement;

	public ElementToChannelConverter(Function<Object, Object> elementToChannel,
			Function<Object, Object> channelToElement) {
		this.elementToChannel = elementToChannel;
		this.channelToElement = channelToElement;
	}

	public ElementToChannelConverter(Function<Object, Object> elementToChannel,
			Function<Object, Object> channelToElement, ElementToChannelConverter nextConverter) {
		this.elementToChannel = elementToChannel.andThen(nextConverter.elementToChannel);
		this.channelToElement = channelToElement.andThen(nextConverter.channelToElement);
	}

	/**
	 * Convert an Element value to a Channel value. If the value can or should not
	 * be converted, this method returns null.
	 * 
	 * @param value
	 * @return the converted value or null
	 */
	public Object elementToChannel(Object value) {
		return this.elementToChannel.apply(value);
	}

	public Object channelToElement(Object value) {
		return this.channelToElement.apply(value);
	}
}