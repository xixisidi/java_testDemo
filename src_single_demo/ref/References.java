package ref;

import java.lang.ref.*;

public class References {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {

		Object weakObj, phantomObj;
		Reference ref;
		WeakReference weakRef;
		PhantomReference phantomRef;
		ReferenceQueue weakQueue, phantomQueue;

		weakObj = new String("Weak Reference");
		phantomObj = new String("Phantom Reference");
		weakQueue = new ReferenceQueue();
		phantomQueue = new ReferenceQueue();
		weakRef = new WeakReference(weakObj, weakQueue);
		phantomRef = new PhantomReference(phantomObj, phantomQueue);

		// Print referents to prove they exist. Phantom referents are
		// inaccessible so we should see a null value.

		System.out.println("Weak Reference: " + weakRef.get() + "_" + weakRef.isEnqueued());
		System.out.println("Phantom Reference: " + phantomRef.get() + "_" + phantomRef.isEnqueued());

		// Clear all strong references
		weakObj = null;
		phantomObj = null;
		// Invoke garbage collector in hopes that references
		// will be queued
		System.gc();
		// See if the garbage collector has queued the references
		System.out.println("Weak Queued: " + weakRef.isEnqueued());
		// Try to finalize the phantom references if not already
		if (!phantomRef.isEnqueued()) {
			System.out.println("Requestion finalization.");
			System.runFinalization();
		}
		System.out.println("Phantom Queued: " + phantomRef.isEnqueued());
		// Wait until the weak reference is on the queue and remove it
		try {
			ref = weakQueue.remove();
			// The referent should be null
			System.out.println("Weak Reference: " + ref.get());
			// Wait until the phantom reference is on the queue and remove it
			ref = phantomQueue.remove();
			System.out.println("Phantom Reference: " + ref.get());
			// We have to clear the phantom referent even though get() returns
			// null
			ref.clear();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
	}
}
