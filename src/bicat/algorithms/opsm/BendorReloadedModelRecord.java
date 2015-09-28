/*
 *                                BiCat is a toolbox that combines different Bi-Clustering and clustering techniques in it, which
 *                                can be applied on a given dataset. This software is the modified version of the original BiCat
 *                                Toolbox implemented at ETH Zurich by Simon Barkow, Eckart Zitzler, Stefan Bleuler, Amela
 *                                Prelic and Don Frick.
 *
 *                                Copyright (c) 2015 Taghi Aliyev
 *
 *                                This file is part of BiCat.
 *
 *                                BiCat is a free software: you can redistribute it and/or modify
 *                                it under the terms of the GNU General Public License as published by
 *                                the Free Software Foundation, either version 3 of the License, or
 *                                (at your option) any later version.
 *
 *                                BiCat is distributed in the hope that it will be useful,
 *                                but WITHOUT ANY WARRANTY; without even the implied warranty of
 *                                MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *                                GNU General Public License for more details.
 *
 *                                You should have received a copy of the GNU General Public License
 *                                along with BiCat.  If not, see <http://www.gnu.org/licenses/>.
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

package bicat.algorithms.opsm;

/**
 * <p>Provides functionality to efficiently record a certain amount of model
 * evaluations, which can be model initializations or model extensions.</p>
 *
 * <p>The record is implemented as a heap with special functionality: While the
 * heap is not full, new elements are put as usual. If the heap is full,
 * the element with lowest score (e.g. the root) is replaced by the new element,
 * if the new element has a higher score than the root. (This corresponds to a
 * conditional pop with subsequent put.)</p>
 *
 * <p>No elements with score 0 are put to the record.</p>
 *
 * <p>There is no 'pop' function. The heap array can be read from outside.</p>
 *
 * <p>Do not change any fields from outside this class. No field hiding is done
 * for performance reasons.</p>
 *
 * @author Thomas Frech
 * @version 2004-07-22
 */
public class BendorReloadedModelRecord
{
	// binary tree as array
	private BendorReloadedModelEvaluation[] element;

	// size = index of last element
	private int size;

	// heap capacity
	private final int capacity;


	/**
	 * Constructor.
	 *
	 * @param capacity constant capacity of the record
	 */
	public BendorReloadedModelRecord(int capacity)
	{
		element = new BendorReloadedModelEvaluation[capacity+1];
		size = 0;
		this.capacity = capacity;
	}

	/**
	 * @return <tt>true</tt> if no element is in record
	 */
	public boolean isEmpty()
	{
		return size==0;
	}

	/**
	 * Put model into record.
	 *
	 * @param newElement model to put into record
	 */
	public void put(BendorReloadedModelEvaluation newElement)
	{
		// do not insert worthless model extensions
		if (newElement==null || newElement.p==0)
			return;

		// put as usual if heap is not full yet
		if (size<capacity)
			putAsUsual(newElement);
		// put by replacing root if new element has better score than root
		else if (newElement.p>element[1].p)
			putByReplacingRoot(newElement);
	}

	// put by replacing root
	private void putByReplacingRoot(BendorReloadedModelEvaluation newElement)
	{
		// replace root by new element
		element[1] = newElement;

		int i = 1;
		int left;
		int right;

		// let new element sink down either left or right as long as both entries exist
		while ((right=(i<<1)+1)<=size && (element[i].p>element[(left=(i<<1))].p || element[i].p>element[right].p))
		{
			if (element[left].p>element[right].p)
			{
				BendorReloadedModelEvaluation buffer = element[right];
				element[right] = element[i];
				element[i] = buffer;
				i = right;
			}
			else
			{
				BendorReloadedModelEvaluation buffer = element[left];
				element[left] = element[i];
				element[i] = buffer;
				i = left;
			}
		}
		// let new element sink down left when only left entry exists
		while ((left=(i<<1))<=size && element[i].p>element[left].p)
		{
			BendorReloadedModelEvaluation buffer = element[left];
			element[left] = element[i];
			element[i] = buffer;
			i = left;
		}
	}

	// put as usual
	private void putAsUsual(BendorReloadedModelEvaluation newElement)
	{
		// add new element at the end of the list
		element[++size] = newElement;

		int i = size;
		int parent;

		// let new element dive up
		while ((parent=(i>>1))>=1 && element[i].p<element[parent].p)
		{
			BendorReloadedModelEvaluation buffer = element[parent];
			element[parent] = element[i];
			element[i] = buffer;
			i = parent;
		}
	}

	// realize all stored model evaluations
	public BendorReloadedModel[] realizeAll()
	{
		BendorReloadedModel[] model = new BendorReloadedModel[size];

		for (int i=0; i<size; i++)
			model[i] = element[i+1].realize();

		return model;
	}

	// realize best stored model evaluation
	public BendorReloadedModel realizeBest()
	{
		if (size==0)
			return null;

		int best = 0;
		double pBest = -1;

		for (int i=1; i<=size; i++)
			if (element[i].p>pBest)
			{
				best = i;
				pBest = element[i].p;
			}

		return element[best].realize();
	}
}
