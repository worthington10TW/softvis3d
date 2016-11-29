import * as React from "react";
import * as ReactDOM from "react-dom";
import * as Actions from "./events/EventInitiator";
import dispatcher from "./events/EventDispatcher";
import { SonarQubeCommunicator } from "./sonarqube";
import Softvis3D from "./components/Softvis3D";
import appStatusStore from "./stores/AppStatusStore";
import cityBuilderStore from "./stores/CityBuilderStore";
import sceneStore from "./stores/SceneStore";
import LegacyConnector from "./legacy/LegacyConnector";

export default class App {
    public constructor() {
        this.bootstrap();
    }

    public bootstrap() {
        const sonar = new SonarQubeCommunicator();
        const legacy = new LegacyConnector();
        dispatcher.register(sonar.handleEvents.bind(sonar));
        dispatcher.register(legacy.handleEvents.bind(legacy));
        dispatcher.register(appStatusStore.handleEvents.bind(appStatusStore));
        dispatcher.register(cityBuilderStore.handleEvents.bind(cityBuilderStore));
        dispatcher.register(sceneStore.handleEvents.bind(sceneStore));
    }

    public init() {
        Actions.initApp();
    }

    public run(target: string) {
        ReactDOM.render(
           <Softvis3D />,
            document.getElementById(target)!
        );
    }
}
