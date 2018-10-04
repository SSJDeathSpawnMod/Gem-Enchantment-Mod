package com.deathspawn.advanced.server;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHandler implements IMessageHandler<DataSending, IMessage>{

	@Override
	public IMessage onMessage(DataSending message, MessageContext ctx) {
		
		return null;
	}
	
}
