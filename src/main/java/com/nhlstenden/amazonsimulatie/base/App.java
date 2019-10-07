package com.nhlstenden.amazonsimulatie.base;

import java.io.IOException;

import com.nhlstenden.amazonsimulatie.controllers.Controller;
import com.nhlstenden.amazonsimulatie.controllers.SimulationController;
import com.nhlstenden.amazonsimulatie.models.World;
import com.nhlstenden.amazonsimulatie.views.DefaultWebSocketView;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;


/*
 * Dit is de hoofdklasse van de applicatie. De app werkt met Spring, een Java
 * framework welke handig is, bijvoorbeeld om een simpele server te schrijven.
 * De main methode zet de app in werking en er wordt een nieuw object gemaakt
 * van de class App. Dit gedeelte handeld Spring voor je af.
 */
@Configuration
@EnableAutoConfiguration
@EnableWebSocket
public class App extends SpringBootServletInitializer implements WebSocketConfigurer {

    /*
     * De main methode regelt het starten van de Spring applicatie. Dit gebeurd
     * middels SpringApplication.run(). Deze zorgt ervoor dat onze App gerund
     * wordt. Dit kan doordat de class App de class SpringBootServletInitializer
     * extend. Dit is een class van Spring welke een server voor ons maakt.
     * De App class is daardoor dus een server.
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    
    //De App is de applicatie en heeft de controller voor de simulatie in zich
    private Controller controller;

   /*
    * De constructor wordt uitgevoerd wanneer de app wordt opgebouwd. Je zult alleen
    * geen new App() tegenkomen. Dit doet Spring namelijk al voor je bij
    * SpringApplication.run().
    */
    public App() {
        this.controller = new SimulationController(new World());
        this.controller.start();
    }

    /*
     * Dit is een standaardmethode van Spring en heeft te maken met het SpringApplication.run()
     * proces.
     */
    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}

    /*
     * Deze methode moet worden geïmplementeerd omdat de class App de interface
     * WebSocketConfigurer implementeerd. Deze interface maakt het mogelijk
     * om zogenaamde WebSocketHandlers te registreren in het systeem. Dit
     * zijn onderdelen in de software die binnenkomende websocket verbindingen
     * afhandelen en iets met de binnenkomende informatie doen, dan wel nieuwe
     * informatie terugsturen naar de client.
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new DefaultWebSocketHandler(), "/connectToSimulation");
    }

    /*
     * Deze class is de standaard WebSocketHandler van dit systeem. Wat hier gebeurd
     * is dat elke keer wanneer een connectie wordt gemaakt met het systeem via een
     * websocket waar deze WebSocketHandler voor is geregistreerd (zie registerWebSocketHandlers),
     * dan wordt de methode afterConnectionEstablished aangeroepen.
     */
    private class DefaultWebSocketHandler extends TextWebSocketHandler {
        /*
         * Binnen deze methode wordt, wanneer een nieuwe websocket connectie wordt gemaakt met
         * de server, een nieuwe view aangemaakt voor die connectie. De view is een
         * DefaultWebSocketView en is de view die wordt gebruikt wanneer we een browser als
         * front-end gebruiken. De sesion variabele is het onderdeel waarmee we informatie
         * kunnen sturen.
         */
        @Override
        public void afterConnectionEstablished(WebSocketSession sesion) {
            controller.addView(new DefaultWebSocketView(sesion));
        }

        /*
         * Via deze methode kunnen binnenkomende berichten van een websocket worden
         * afgehandeld. De berichten zijn gekoppeld aan een bepaalde sessie. Deze methode
         * is niet in gebruik voor de standaardcode die gegeven is. Je kunt zelf wel een
         * implementatie maken.
         */
        @Override
        public void handleTextMessage(WebSocketSession session, TextMessage message) {
            //Do something to handle user input here
        }

        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) throws IOException {
            session.close(CloseStatus.SERVER_ERROR);
        }
    }
}