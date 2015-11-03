/*
 *                                BBiCat is a toolbox that combines different Bi-Clustering and clustering techniques in it, which
 *                                can be applied on a given dataset. This software is the modified version of the original BiCat
 *                                Toolbox implemented at ETH Zurich by Simon Barkow, Eckart Zitzler, Stefan Bleuler, Amela
 *                                Prelic and Don Frick.
 *
 *                                DOI for citing the release : 10.5281/zenodo.33117
 *
 *                                Copyright (c) 2015 Taghi Aliyev, Marco Manca, Alberto Di Meglio
 *
 *                                This file is part of BBiCat.
 *
 *                                BBiCat is a free software: you can redistribute it and/or modify
 *                                it under the terms of the GNU General Public License as published by
 *                                the Free Software Foundation, either version 3 of the License, or
 *                                (at your option) any later version.
 *
 *                                BBiCat is distributed in the hope that it will be useful,
 *                                but WITHOUT ANY WARRANTY; without even the implied warranty of
 *                                MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *                                GNU General Public License for more details.
 *
 *                                You should have received a copy of the GNU General Public License
 *                                along with BBiCat.  If not, see <http://www.gnu.org/licenses/>.
 *
 *                                You can contact the author at the following email address:
 *                                taghi.aliyev@cern.ch
 *
 *                                PLEASE NOTE THAT ORIGINAL GROUP AT ETH ZURICH HAS BEEN DISSOLVED AND SO,
 *                                CONTACTING TAGHI ALIYEV MIGHT BE MORE BENEFITIAL FOR ANY QUESTIONS.
 *
 *                                IN NO EVENT SHALL THE AUTHORS AND MAINTAINERS OF THIS SOFTWARE BE LIABLE TO
 *                                ANY PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 *                                ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE
 *                                AUTHORS AND MAINTAINERS HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *                                AUTHORS AND THE MAINTAINERS SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 *                                BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 *                                FOR A PARTICULAR PURPOSE. THE SOFTWARE PROVIDED HEREUNDER IS ON AN "AS IS"
 *                                BASIS, AND THE AUTHORS AND THE MAINTAINERS HAS NO OBLIGATION TO PROVIDE
 *                                MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS
 *
 *                                COPYRIGHT NOTICE FROM THE ORIGINAL SOFTWARE:
 *
 *                                Copyright notice : Copyright (c) 2005 Swiss Federal Institute of Technology, Computer
 *                                Engineering and Networks Laboratory. All rights reserved.
 *                                BicAT - A Biclustering Analysis Toolbox
 *                                IN NO EVENT SHALL THE SWISS FEDERAL INSTITUTE OF TECHNOLOGY, COMPUTER
 *                                ENGINEERING AND NETWORKS LABORATORY BE LIABLE TO ANY PARTY FOR
 *                                DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING
 *                                OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE
 *                                SWISS FEDERAL INSTITUTE OF TECHNOLOGY, COMPUTER ENGINEERING AND
 *                                NETWORKS LABORATORY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 *                                DAMAGE.
 *
 *                                THE SWISS FEDERAL INSTITUTE OF TECHNOLOGY, COMPUTER ENGINEERING AND
 *                                NETWORKS LABORATORY, SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 *                                BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *                                FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE PROVIDED HEREUNDER IS
 *                                ON AN "AS IS" BASIS, AND THE SWISS FEDERAL INSTITUTE OF TECHNOLOGY,
 *                                COMPUTER ENGINEERING AND NETWORKS LABORATORY HAS NO OBLIGATION
 *                                TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 *                                MODIFICATIONS.
 */

package bicat.run_machine;

import javax.swing.SwingUtilities;

/**
 * This is the class used by the GUI element in order to have parallel thread where computation are done
 * However, this feature is disabled at the moment, at it makes console program to misbehave at times.
 *
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public abstract class SwingWorker {

  private Object value; // see getValue(), setValue();

  // ===========================================================================
  /**
   * class to maintain reference to current worker thread under separate
   * synchronization control
   *
   * */
  private static class ThreadVar {
    private Thread thread;
    ThreadVar(Thread t) { thread = t; }
    synchronized Thread get() { return thread; }
    synchronized void clear() { thread = null; }
  }

  private ThreadVar threadVar;

  // ===========================================================================
  /**
   * Start a thread that will call the <code>construct</code> method and then
   * exit.
   *
   * */
  public SwingWorker() {
    final Runnable doFinished = new Runnable() {
      public void run() { finished(); }
    };

    Runnable doConstruct = new Runnable() {
      public void run() {
        try {
          setValue(construct());
        }
        finally {
          threadVar.clear();
        }
        SwingUtilities.invokeLater(doFinished);
      }
    };

    Thread t = new Thread(doConstruct);
    threadVar = new ThreadVar(t);
  }

  // ===========================================================================
  /**
   * Start the woker thread
   *
   * */
  public void start() {
    Thread t = threadVar.get();
    if(t != null) t.start();
  }

  // ===========================================================================
  /**
   * Get the value produced by the worker thread, or null if it hasn't been
   * constructed yet.
   *
   * */
  protected synchronized Object getValue() { return value; }

  // ===========================================================================
  /**
   * Set the value produced by the worker thread.
   *
   * */
  protected synchronized void setValue(Object x) { value = x; }

  // ===========================================================================
  /**
   * Compute the value to be returned by the <code>get</code> method.
   *
   * */
  public abstract Object construct();

  // ===========================================================================
  /**
   * Called on the event dispatching thread (not on the worker thread) after the
   * <code>construct</code> method has returned.
   *
   * */
  public void finished() {}

  // ===========================================================================
  /**
   * A new method that interrupts the worker thread. Call this method to force
   * the worker to stop what it's doing.
   *
   * */
  public void interrupt() {
    Thread t = threadVar.get();
    if(t != null) t.interrupt();
    threadVar.clear();
  }

  // ===========================================================================
  /**
   * Return the value created by the <code>construct</code> method. Returns null
   * if either the constructing thread ir teh current thread was interrupted
   * before a value was produced.
   *
   * @return the value created by the <code>construct</code> method
   *
   * */
  public Object get() {
    while(true) {
      Thread t = threadVar.get();
      if(t == null) return getValue();
      try {
        t.join();
      } catch(InterruptedException e) {
        Thread.currentThread().interrupt(); // propagate
        return null;
      }
    }
  }

  // ===========================================================================

}
