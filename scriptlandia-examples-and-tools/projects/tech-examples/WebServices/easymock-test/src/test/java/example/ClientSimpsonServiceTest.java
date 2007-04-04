package example;

import static org.easymock.EasyMock.aryEq;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class ClientSimpsonServiceTest extends TestCase {

	private IEpisode episode17Mock;

	private IEpisode episode42Mock;

	private ISimpsonService remoteSimpsonServiceMock;

	protected void setUp() throws Exception {
		super.setUp();
		episode17Mock = createMock(IEpisode.class);
		episode42Mock = createMock(IEpisode.class);
		remoteSimpsonServiceMock = createMock(ISimpsonService.class);
	}

	public void testClientSimpsonService() {
		try {
			new ClientSimpsonService(null);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// expected
		}
		new ClientSimpsonService(remoteSimpsonServiceMock);
	}

	public void testGetEpisode() throws Exception {
		expect(remoteSimpsonServiceMock.getEpisode(17))
				.andReturn(episode17Mock);
		replay(remoteSimpsonServiceMock);

		ISimpsonService clientSimpsonService = new ClientSimpsonService(
				remoteSimpsonServiceMock);
		IEpisode result = clientSimpsonService.getEpisode(17);

		verify(remoteSimpsonServiceMock);
		assertEquals(episode17Mock, result);
	}

	public void testGetEpisodeCaches() throws Exception {
		expect(remoteSimpsonServiceMock.getEpisode(17))
		.andReturn(episode17Mock);
		expect(remoteSimpsonServiceMock.getEpisode(42))
		.andReturn(episode42Mock);
		replay(remoteSimpsonServiceMock);

		ISimpsonService clientSimpsonService = new ClientSimpsonService(
				remoteSimpsonServiceMock);
		assertEquals(episode17Mock, clientSimpsonService.getEpisode(17));
		assertEquals(episode42Mock, clientSimpsonService.getEpisode(42));
		// not expected since it should be cached
		assertEquals(episode17Mock, clientSimpsonService.getEpisode(17));
		assertEquals(episode42Mock, clientSimpsonService.getEpisode(42));

		verify(remoteSimpsonServiceMock);
	}

	public void testGetEpisodeThrowsException() throws Exception {
		EpisodeNotFoundException exception = new EpisodeNotFoundException();
		expect(remoteSimpsonServiceMock.getEpisode(666)).andThrow(exception);
		replay(remoteSimpsonServiceMock);

		ISimpsonService clientSimpsonService = new ClientSimpsonService(
				remoteSimpsonServiceMock);
		try {
			clientSimpsonService.getEpisode(666);
			fail("Expected EpisodeNotFoundException");
		} catch (EpisodeNotFoundException e) {
			// expected
		}

		verify(remoteSimpsonServiceMock);
	}

	public void testGetEpisodes() {
		List<IEpisode> expectedResult = Arrays.asList(new IEpisode[] {
				episode17Mock, episode42Mock });
		expect( remoteSimpsonServiceMock.getEpisodes(aryEq(new int[] { 17, 42 }))).
			andReturn(expectedResult);
		replay(remoteSimpsonServiceMock);

		ISimpsonService clientSimpsonService = new ClientSimpsonService(
				remoteSimpsonServiceMock);
		assertEquals(expectedResult, clientSimpsonService
				.getEpisodes(new int[] { 17, 42 }));

		verify(remoteSimpsonServiceMock);
	}

	public void testGetEpisodesCaches() throws Exception {
		expect(remoteSimpsonServiceMock.getEpisode(17))
				.andReturn(episode17Mock);
		expect(remoteSimpsonServiceMock.getEpisodes(aryEq(new int[] { 42 })))
				.andReturn(Arrays.asList(new IEpisode[] { episode42Mock }));
		replay(remoteSimpsonServiceMock);

		ISimpsonService clientSimpsonService = new ClientSimpsonService(
				remoteSimpsonServiceMock);
		assertEquals(episode17Mock, clientSimpsonService.getEpisode(17));
		assertEquals(Arrays.asList(new IEpisode[] { episode17Mock,
				episode42Mock }), clientSimpsonService.getEpisodes(new int[] {
				17, 42 }));
		assertEquals(Arrays.asList(new IEpisode[] { episode17Mock,
				episode42Mock }), clientSimpsonService.getEpisodes(new int[] {
				17, 42 }));

		verify(remoteSimpsonServiceMock);
	}

}
