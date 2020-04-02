package com.indiehood.app;

        import com.indiehood.app.ui.favorites.Artist;
        import com.indiehood.app.ui.listings.ShowListing;

        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.mockito.runners.MockitoJUnitRunner;

        import static com.google.common.truth.Truth.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ShowListingTests {
    @Test
    public void defaultConstructor_works() {
            ShowListing testListing = new ShowListing();
            assertThat(testListing).isNotNull();
        }

    @Test
    public void addShowListing_works() {
        ShowListing testListing = new ShowListing();
        String showBand = "testBand";
        testListing.setBandName("testBand");
        assertThat(testListing.getBandName()).isEqualTo(showBand);
    }


    public void getFreePrice(){
        ShowListing testListing = new ShowListing();
        String freePrice = "FREE";
        testListing.setPrice(0.0);
        assertThat(testListing.getStringifiedPrice()).isEqualTo(freePrice);
    }

}