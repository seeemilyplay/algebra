package seeemilyplay.core.international;

/**
 * A factory that makes instances of a certain class depending
 * on the current locality.
 *
 * @param <T>	the type of the objects this factory creates
 */
public final class InternationalFactory<T> {

	private final LocalFactory<T> localFactory;

	public InternationalFactory(LocalFactory<T> localFactory) {
		this.localFactory = localFactory;
	}

	public T create() {
		return localFactory.createUK();
	}

	public static <T> T create(LocalFactory<T> localFactory) {
		InternationalFactory<T> internationalFactory =
			new InternationalFactory<T>(localFactory);
		return internationalFactory.create();
	}
}
