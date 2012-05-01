package seeemilyplay.core.international;

/**
 * A factory that makes instances of a particular class
 * for a each locality.  Use with an <code>InternationalFactory</code>.
 *
 * @param <T>	the type of the objects this factory creates
 *
 * @see InternationalFactory
 */
public interface LocalFactory<T> {

	public T createUK();
}
