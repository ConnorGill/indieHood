package com.indiehood.app;

import com.indiehood.app.ui.favorites.Artist;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class ArtistValidatorTest {
    @Test
    public void defaultConstructor_works() {
        Artist testArtist = new Artist();
        assertThat(testArtist).isNotNull();
    }
    @Test
    public void artistName_isSet() {
        //
    }
}