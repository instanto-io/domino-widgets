package io.instanto.domino.testing;

import io.instanto.cucumber.tea.CucumberSuite;

@CucumberSuite(value = "features/gallery-components.feature", runner = DominoRunner.class)
public final class GalleryComponentsSteps extends GallerySteps {}
