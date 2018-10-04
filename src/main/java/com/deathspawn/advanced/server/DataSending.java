package com.deathspawn.advanced.server;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class DataSending implements IMessage{
	
	public DataSending(){}
	
	private int toSend;
	private char variableUse;
	private short guiId;
	  public DataSending(int toSend, char variableUse, short guiId) {
	    this.toSend = toSend;
	    this.variableUse = variableUse;
	    this.guiId = guiId;
	  }
	
	@Override
	public void fromBytes(ByteBuf buf) {
		buf.writeChar(Character.valueOf(this.variableUse));
		buf.writeShort(guiId);
		buf.writeInt(this.toSend);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		this.toSend = buf.readChar();
		this.guiId = buf.readShort();
		this.toSend = buf.readInt();
	}

}
