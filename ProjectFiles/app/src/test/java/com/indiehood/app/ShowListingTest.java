package com.indiehood.app;

import com.indiehood.app.ui.listings.ShowListing;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ThreadLocalRandom;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ShowListingTest {

    @Test
    public void defaultConstructor_notNull() {
        ShowListing show = new ShowListing(showID);
        assertNotNull(show);
    }

    @Test
    public void peopleInterested_1() {
        ShowListing show = new ShowListing(showID);
        show.setNumberInterested(1);
        assertEquals(show.getInterestedText(), "1 person is interested");
    }

    @Test
    public void peopleInterested_not1() {
        ShowListing show = new ShowListing(showID);
        int randomNumber = ThreadLocalRandom.current().nextInt(4) + 2;
        show.setNumberInterested(randomNumber);
        assertEquals(randomNumber + " people are interested", show.getInterestedText());
    }

    @Test
    public void showPrice_0() {
        ShowListing show = new ShowListing(showID);
        show.setPrice(0.0);
        assertEquals(show.getStringifiedPrice(), "FREE");
    }

    @Test
    public void showPrice_not0() {
        ShowListing show = new ShowListing(showID);
        int randomNumber = ThreadLocalRandom.current().nextInt(4) + 1;
        show.setPrice((double) randomNumber);
        assertEquals("$" + randomNumber + ".00", show.getStringifiedPrice());
    }







}
