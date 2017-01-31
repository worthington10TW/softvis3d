import * as React from "react";
import { expect } from "chai";
import { shallow } from "enzyme";
import Visualization from "../../../src/components/visualization/Visualization";
import Scene from "../../../src/components/scene/Scene";
import { CityBuilderStore } from "../../../src/stores/CityBuilderStore";
import { SceneStore } from "../../../src/stores/SceneStore";
import TopBar from "../../../src/components/topbar/TopBar";
import SideBar from "../../../src/components/sidebar/SideBar";

describe("<Visualization/>", () => {

    it("should not render any children, when no visualization (shapes) is ready", () => {
        let localCityBuilderStore: CityBuilderStore = new CityBuilderStore();
        let localSceneStore: SceneStore = new SceneStore();

        let parentId: string = "parentsduhfisdfuh";
        let expectedParentElement: TreeElement = createTestTreeElement(parentId);

        let testId: string = "siudgffsiuhdsfiu2332";
        let expectedSelectedElement: TreeElement = createTestTreeElement(testId);
        expectedSelectedElement.parentId = expectedParentElement.id;

        expectedParentElement.children.push(expectedSelectedElement);

        localSceneStore.shapes = null;

        localSceneStore.legacyData = expectedParentElement;
        localSceneStore.selectedObjectId = testId;

        const visualization = shallow(
            <Visualization cityBuilderStore={localCityBuilderStore} sceneStore={localSceneStore}/>
        );

        expect(visualization.children()).to.have.length(0);
        expect(visualization.find("div")).to.have.length(1);
    });

    it("should initialize all elements on start - shapes available but empty", () => {
        let localCityBuilderStore: CityBuilderStore = new CityBuilderStore();
        let localSceneStore: SceneStore = new SceneStore();

        let testId: string = "siudgffsiuhdsfiu2332";
        let expectedSelectedElement: TreeElement = createTestTreeElement(testId);

        localSceneStore.shapes = {};

        localSceneStore.legacyData = expectedSelectedElement;
        localSceneStore.selectedObjectId = testId;

        const visualization = shallow(
            <Visualization cityBuilderStore={localCityBuilderStore} sceneStore={localSceneStore}/>
        );

        expect(visualization.children()).to.have.length(3);
        expect(visualization.contains(
            <TopBar cityBuilderStore={localCityBuilderStore} sceneStore={localSceneStore}/>)).to.be.true;
        expect(visualization.contains(
            <Scene sceneStore={localSceneStore} />)).to.be.true;
        expect(visualization.contains(
            <SideBar sceneStore={localSceneStore}/>)).to.be.true;
    });

});

function createTestTreeElement(id: string): TreeElement {
    return {
        id,
        name: "",
        isNode: false,

        children: [],

        measures: {},
        parentId: null
    };
}