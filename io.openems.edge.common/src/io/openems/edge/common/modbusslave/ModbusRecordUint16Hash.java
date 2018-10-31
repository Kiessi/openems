package io.openems.edge.common.modbusslave;

public class ModbusRecordUint16Hash extends ModbusRecordUint16 {

	private final String text;

	public ModbusRecordUint16Hash(int offset, String text) {
		super(offset, (short) "OpenEMS".hashCode());
		this.text = text;
	}

	@Override
	public String toString() {
		return "ModbusRecordUint16Hash [value=" + text + "/0x" + Integer.toHexString(this.value)
				+ ", type=" + getType() + "]";
	}

}