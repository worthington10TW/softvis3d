import * as React from "react";
import { observer } from "mobx-react";
import { SceneStore } from "../../stores/SceneStore";
import SoftVis3dScene from "./visualization/SoftVis3dScene";
import SceneInformation from "./information/SceneInformation";

interface SceneProps {
    sceneStore: SceneStore;
}

/**
 * Responsible for the drawing the canvas for the visualization.
 */
@observer
export default class Scene extends React.Component<SceneProps, any> {

    private mouseMoved: boolean = false;

    constructor() {
        super();
    }

    public componentDidMount() {
        this.props.sceneStore.scenePainter.init();
        this.props.sceneStore.refreshScene = true;
        this.props.sceneStore.sceneComponentIsMounted = true;

        if (this.props.sceneStore.selectedObjectId) {
            this.props.sceneStore.scenePainter.selectSceneTreeObject(this.props.sceneStore.selectedObjectId);
        }

        this.updateCameraPosition();
    }

    public componentWillUnmount() {
        this.props.sceneStore.sceneComponentIsMounted = false;
    }

    public render() {
        const {sceneStore} = this.props;

        return (
            <div className="scene">
                <canvas id={SoftVis3dScene.CANVAS_ID}
                        onMouseDown={() => { this.mouseMoved = false; }}
                        onMouseMove={() => { this.mouseMoved = true; }}
                        onMouseUp={(e) => { this.onMouseUp(e); }}
                        />
                <SceneInformation sceneStore={sceneStore}/>
           </div>
        );
    }

    // public for tests
    public updateCameraPosition() {
        this.props.sceneStore.cameraPosition = this.props.sceneStore.scenePainter.getCamera().position;
    }

    private onMouseUp(event: any) {
        this.updateCameraPosition();

        if (!this.mouseMoved) {
            this.props.sceneStore.selectedObjectId = this.props.sceneStore.scenePainter.makeSelection(event);
        }
    }

}
