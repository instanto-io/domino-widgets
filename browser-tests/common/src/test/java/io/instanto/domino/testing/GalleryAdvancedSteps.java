package io.instanto.domino.testing;

import io.instanto.cucumber.tea.CucumberSuite;

@CucumberSuite(value = "features/gallery-advanced.feature", runner = DominoRunner.class)
public final class GalleryAdvancedSteps extends GallerySteps {}
