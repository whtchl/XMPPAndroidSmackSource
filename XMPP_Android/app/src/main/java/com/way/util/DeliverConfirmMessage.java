package com.way.util;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.jivesoftware.smackx.receipts.DeliveryReceipt;
import org.xmlpull.v1.XmlPullParser;

/**
 * Created by tchl on 2016-09-05.
 */
public class DeliverConfirmMessage implements PacketExtension {

    public static final String ELEMENT = "confirm";
    public static final String NAMESPACE = "urn:xmpp:confirm";


    @Override
    public String getElementName() {
        return DeliverConfirmMessage.ELEMENT;
    }

    @Override
    public String getNamespace() {
        return DeliverConfirmMessage.NAMESPACE;
    }

    public String toXML()
    {
        return "<confirm xmlns='" + DeliverConfirmMessage.NAMESPACE + "'/>";
    }

    /**
     * This Provider parses and returns DeliveryReceiptRequest packets.
     */
    public static class Provider implements PacketExtensionProvider {
        @Override
        public PacketExtension parseExtension(XmlPullParser parser) {
            return new DeliverConfirmMessage();
        }
    }
}
