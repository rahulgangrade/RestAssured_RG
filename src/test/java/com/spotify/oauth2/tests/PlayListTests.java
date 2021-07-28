package com.spotify.oauth2.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import com.spotify.oauth2.utils.StatusCode;

import static com.spotify.oauth2.utils.FakerUtils.*;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;



@Epic("Spotify OAuth2.0")
@Feature("Testing Playlist API")
public class PlayListTests extends BaseTest{

	
	@Story("Create Playlist Story")
	@TmsLink("Test-1")
	@Issue("12345")
	@Link("https://www.google.com")
	@Description("This is description")
	@Test(description = "should be able to create playlist")
	public void ShouldBeAbletoCreatePlaylist() {
		Playlist requestPlaylist = playListBuilder(generateName(),generateDescription(), false);
		Response response = PlaylistApi.post(requestPlaylist);
		assertStatusCode(response.statusCode(), StatusCode.CODE_201);

		Playlist responsePlaylist = response.as(Playlist.class);
		assertPlaylistEqual(responsePlaylist, requestPlaylist);

	}

	@Test
	public void ShouldBeAbletoGetAPlaylist() {
		Playlist requestPlaylist = playListBuilder("Updated Playlist Name111", "Updated playlist description111",false);

		Response response = PlaylistApi.get(DataLoader.getInstance().getPlaylistId());
		assertStatusCode(response.statusCode(), StatusCode.CODE_200);

		Playlist responsePlaylist = response.as(Playlist.class);
		assertPlaylistEqual(responsePlaylist, requestPlaylist);

	}

	@Test
	public void ShouldBeAbletoUpdatePlaylist() {
		Playlist requestPlaylist = playListBuilder("Updated Playlist Name111","Updated playlist description111",
				false);

		Response response = PlaylistApi.update(DataLoader.getInstance().getPlaylistId(), requestPlaylist);
		assertStatusCode(response.statusCode(), StatusCode.CODE_200);

	}
    @Step
	public void assertError(Error error, StatusCode statusCode) {
		assertThat(error.getError().getStatus(), equalTo(statusCode.code));
		assertThat(error.getError().getMessage(), equalTo(statusCode.msg));
	}
	@Story("Create Playlist Story")
	@Test
	public void ShouldNotBeAbletoCreatePlaylistWithoutName() {
		Playlist requestPlaylist = playListBuilder("", generateDescription(), false);

		Response response = PlaylistApi.post(requestPlaylist);
		assertStatusCode(response.statusCode(), StatusCode.CODE_400);

		Error error = response.as(Error.class);
		assertError(error, StatusCode.CODE_400);

	}
	@Story("Create Playlist Story")
	@Test
	public void ShouldNotBeAbletoCreatePlaylistWithExpiredToken() {
		String invalid_token = "12345";
		Playlist requestPlaylist = playListBuilder(generateName(), generateDescription(), false);

		Response response = PlaylistApi.post(invalid_token, requestPlaylist);
		assertStatusCode(response.statusCode(), StatusCode.CODE_401);

		Error error = response.as(Error.class);
		assertError(error, StatusCode.CODE_401);

	}
	
	@Step
	public Playlist playListBuilder(String Name, String description, boolean _public) {
		return new Playlist().
		     setName(Name).
		     setDescription(description).
		     setPublic(_public);
	}
	@Step
	public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist) {

		assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
		assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
		assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
	}
	@Step
	public void assertStatusCode(int actualStatusCode, StatusCode statusCode) {

		assertThat(actualStatusCode, equalTo(statusCode.code));
	}

}
