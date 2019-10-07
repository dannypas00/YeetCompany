package com.nhlstenden.amazonsimulatie.views;

import java.io.IOException;

import com.nhlstenden.amazonsimulatie.base.Command;
import com.nhlstenden.amazonsimulatie.models.Object3D;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/*
 * Deze class is de standaard websocketview. De class is een andere variant
 * van een gewone view. Een "normale" view is meestal een schermpje op de PC,
 * maar in dit geval is het wat de gebruiker ziet in de browser. Het behandelen
 * van een webpagina als view zie je vaker wanneer je te maken hebt met
 * serversystemen. In deze class wordt de WebSocketSession van de client opgeslagen,
 * waarmee de view class kan communiceren met de browser.
 */
public class DefaultWebSocketView implements View {
    private WebSocketSession sesion;
    private Command onClose;

    public DefaultWebSocketView(WebSocketSession sesion) {
        this.sesion = sesion;
    }

    /*
     * Deze methode wordt aangroepen vanuit de controller wanneer er een update voor
     * de views is. Op elke view wordt dan de update methode aangroepen, welke een
     * JSON pakketje maakt van de informatie die verstuurd moet worden. Deze JSON
     * wordt naar de browser verstuurd, welke de informatie weer afhandeld.
     */
    @Override
    public void update(String event, Object3D data) {
        try {
            if(this.sesion.isOpen()) {
                this.sesion.sendMessage(new TextMessage("{"
                + surroundString("command") + ": " + surroundString(event) + ","
                + surroundString("parameters") + ": " + jsonifyObject3D(data)
              + "}"));
            }
            else {
                this.onClose.execute();
            }
            
        } catch (IOException e) {
            this.onClose.execute();
        }
    }

    @Override
    public void onViewClose(Command command) {
        onClose = command;
    }

    /*
     * Deze methode maakt van een Object3D object een JSON pakketje om verstuurd te worden
     * naar de client.
     */
    private String jsonifyObject3D(Object3D object) {
        return  "{" 
                + surroundString("uuid") + ":" + surroundString(object.getUUID()) + ","
                + surroundString("type") + ":" + surroundString(object.getType()) + ","
                + surroundString("x") + ":" + object.getX() + ","
                + surroundString("y") + ":" + object.getY() + ","
                + surroundString("z") + ":" + object.getZ() + ","
                + surroundString("rotationX") + ":" + object.getRotationX() + ","
                + surroundString("rotationY") + ":" + object.getRotationY() + ","
                + surroundString("rotationZ") + ":" + object.getRotationZ()
              + "}";
    }

    private String surroundString(String s) {
        return "\"" + s + "\"";
    }
}