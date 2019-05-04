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


public class testdrt {

    private static final String COTTBUS_DOOR2DOOR_CONFIG = "/Users/vishal/git/matsim-maas/scenarios/test/config.xml";


    public static void run(Config config, boolean otfvis) {
        //Creates a MATSim Controler and preloads all DRT related packages
        config.controler().setOverwriteFileSetting( OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists );
        config.controler().setLastIteration(10);
        //config.qsim().setFlowCapFactor(0.1);
        //config.qsim().setStorageCapFactor(0.1);
        //config.counts().setCountsScaleFactor(10);
        config.global().setCoordinateSystem("Atlantis");
        //config.planCalcScore().setBrainExpBeta(1);
        //config.planCalcScore().setLearningRate(1);
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
