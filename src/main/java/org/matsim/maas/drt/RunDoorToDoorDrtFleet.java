/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2017 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package org.matsim.maas.drt;

import org.matsim.contrib.av.robotaxi.fares.drt.DrtFareModule;
import org.matsim.contrib.av.robotaxi.fares.drt.DrtFaresConfigGroup;
import org.matsim.contrib.drt.run.DrtConfigGroup;
import org.matsim.contrib.drt.run.DrtControlerCreator;
import org.matsim.contrib.dvrp.run.DvrpConfigGroup;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.vis.otfvis.OTFVisConfigGroup;

/**
 * @author jbischoff
 * An example to run the demand responsive transport contribution in MATSim, with DRT service
 * serving customers from door to door between origin and destinations.
 * There are two different examples, one for Mielec and one for the Cottbus scenario.
 * You'll find the corresponding scenarios in the "scenario" folder of the project.
 */
public class RunDoorToDoorDrtFleet {

    private static final String COTTBUS_DOOR2DOOR_CONFIG = "/Users/vishal/Downloads/input/config.xml";


	public static void run(Config config, boolean otfvis) {
		//Creates a MATSim Controler and preloads all DRT related packages
		config.controler().setOverwriteFileSetting( OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists );
		config.controler().setLastIteration(100);
		//config.qsim().setFlowCapFactor(0.1);
		//config.qsim().setStorageCapFactor(0.1);
		config.counts().setCountsScaleFactor(10);
		config.global().setCoordinateSystem("EPSG:31468");
		config.planCalcScore().setBrainExpBeta(1);
		config.planCalcScore().setLearningRate(1);
		//config.counts().setInputFile("munichCounts.xml");

		Controler controler = DrtControlerCreator.createControlerWithSingleModeDrt(config, otfvis);


		//this is optional, adds fares to DRT
		//controler.addOverridingModule(new DrtFareModule());

		//starts the simulation
		controler.run();
	}

	public static void main(String[] args) {

		Config config = ConfigUtils.loadConfig(COTTBUS_DOOR2DOOR_CONFIG, new DrtConfigGroup(), new DvrpConfigGroup(),
				new OTFVisConfigGroup(), new DrtFaresConfigGroup());

		run(config, false);
	}
}
