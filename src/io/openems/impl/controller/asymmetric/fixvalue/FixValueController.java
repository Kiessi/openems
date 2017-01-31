package io.openems.impl.controller.asymmetric.fixvalue;

import java.util.Set;

import io.openems.api.channel.ConfigChannel;
import io.openems.api.controller.Controller;
import io.openems.api.doc.ConfigInfo;
import io.openems.api.exception.InvalidValueException;
import io.openems.api.exception.WriteChannelException;

public class FixValueController extends Controller {

	@ConfigInfo(title = "ess to set fix power value", type = Ess.class)
	public final ConfigChannel<Set<Ess>> esss = new ConfigChannel<Set<Ess>>("esss", this);

	@ConfigInfo(title = "Fixvalue activePower for phase 1", type = Long.class)
	public final ConfigChannel<Long> activePowerL1 = new ConfigChannel<>("activePowerL1", this);
	@ConfigInfo(title = "Fixvalue activePower for phase 2", type = Long.class)
	public final ConfigChannel<Long> activePowerL2 = new ConfigChannel<>("activePowerL2", this);
	@ConfigInfo(title = "Fixvalue activePower for phase 3", type = Long.class)
	public final ConfigChannel<Long> activePowerL3 = new ConfigChannel<>("activePowerL3", this);

	@ConfigInfo(title = "Fixvalue reactivePower for phase 1", type = Long.class)
	public final ConfigChannel<Long> reactivePowerL1 = new ConfigChannel<>("reactivePowerL1", this);
	@ConfigInfo(title = "Fixvalue reactivePower for phase 2", type = Long.class)
	public final ConfigChannel<Long> reactivePowerL2 = new ConfigChannel<>("reactivePowerL2", this);
	@ConfigInfo(title = "Fixvalue reactivePower for phase 3", type = Long.class)
	public final ConfigChannel<Long> reactivePowerL3 = new ConfigChannel<>("reactivePowerL3", this);

	public FixValueController() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FixValueController(String thingId) {
		super(thingId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		try {
			for (Ess ess : esss.value()) {
				try {
					ess.setActivePowerL1.pushWrite(activePowerL1.value());
				} catch (WriteChannelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					ess.setActivePowerL2.pushWrite(activePowerL2.value());
				} catch (WriteChannelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					ess.setActivePowerL3.pushWrite(activePowerL3.value());
				} catch (WriteChannelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					ess.setReactivePowerL1.pushWrite(reactivePowerL1.value());
				} catch (WriteChannelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					ess.setReactivePowerL2.pushWrite(reactivePowerL2.value());
				} catch (WriteChannelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					ess.setReactivePowerL3.pushWrite(reactivePowerL3.value());
				} catch (WriteChannelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (InvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
