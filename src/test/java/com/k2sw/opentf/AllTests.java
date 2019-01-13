package com.k2sw.opentf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        GameStateTest.class,
        StandardCardsTest.class
})
public class AllTests {}
