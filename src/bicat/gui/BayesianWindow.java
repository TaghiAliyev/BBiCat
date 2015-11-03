/*
 *                                BBiCat is a toolbox that combines different Bi-Clustering and clustering techniques in it, which
 *                                can be applied on a given dataset. This software is the modified version of the original BiCat
 *                                Toolbox implemented at ETH Zurich by Simon Barkow, Eckart Zitzler, Stefan Bleuler, Amela
 *                                Prelic and Don Frick.
 *
 *                                DOI for citing the release : 10.5281/zenodo.33117
 *
 *                                Copyright (c) 2015 Taghi Aliyev
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

package bicat.gui;

import MetaFramework.Bayesian;
import bicat.Main.UtilFunctionalities;
import bicat.biclustering.Dataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;

/**
 * GUI element that will create and show the Bayesian analysis window
 * Idea is to have bunch of radio buttons and let user define how genes are grouped
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class BayesianWindow {

    private BicatGui owner;
    private UtilFunctionalities engine;
    private JDialog dialog;

    public BayesianWindow(BicatGui owner) {
        this.owner = owner;
        this.engine = owner.getUtilEngine();
    }

    public BayesianWindow(UtilFunctionalities engine) {
        this.owner = null;
        this.engine = engine;
    }

    public BayesianWindow() {
        this.owner = null;
        this.engine = new UtilFunctionalities();
    }

    public void show() {
        dialog = new JDialog(owner, "Assess the biclusters");
        ButtonGroup choices = new ButtonGroup();

        // For each column we should add a radio button
        JLabel label = new JLabel("Please choose the columns that will differentiate the genes into groups");
//        dialog.add(label);

        // Probably should add every column as a possibility
        Dataset dataset = engine.getCurrentDataset();

        JPanel contentPane = new JPanel(new GridLayout(0, 1));

        contentPane.setBorder(BorderFactory.createTitledBorder(BorderFactory
                .createLineBorder(Color.black), "Choose the column"));


        contentPane.add(label);
        Vector<String> genes = dataset.getChipNames();
        for (String tmp : genes) {
            JRadioButton columnButton = new JRadioButton(tmp);
            columnButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("I am the chosen one!!! And my name is " + tmp);
                }
            });
            choices.add(columnButton);
            contentPane.add(columnButton);
        }

        JRadioButton diffExpression = new JRadioButton("Differential Expression over all samples/columns");
        choices.add(diffExpression);
        contentPane.add(diffExpression);


        JScrollPane scrollPane = new JScrollPane(contentPane);
        Dimension d = new Dimension(contentPane.getComponent(0).getPreferredSize());
        Dimension d2 = new Dimension(contentPane.getComponent(1).getPreferredSize());
        d.width *= 1.5;
        d2.height *= 10;
        scrollPane.setPreferredSize(new Dimension(d.width, d2.height));
        scrollPane.setViewportView(contentPane);

        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory
                .createLineBorder(Color.black), "Choose the column"));

        scrollPane.revalidate();

        JPanel buttons = new JPanel(new FlowLayout());
        JButton goButton = new JButton("Go!");
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Bayesian bayesian = null;
                try {
                    bayesian = new Bayesian();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                int chosenIndex = -1;
                int counter = 0;
                for (Enumeration<AbstractButton> buttons = choices.getElements(); buttons.hasMoreElements();) {
                    AbstractButton button = buttons.nextElement();

                    if (button.isSelected()) {
                        if (counter == choices.getButtonCount() - 1)
                            chosenIndex = -1; // Diff Expression
                        else
                            chosenIndex = counter;
                    }
                    counter++;
                }
                bayesian.compute(null, null, dataset, chosenIndex);
            }
        });
        buttons.add(goButton);

        JPanel allTogether = new JPanel(new GridLayout(0, 1));
        allTogether.add(scrollPane);
        allTogether.add(buttons);

        dialog.setContentPane(allTogether);
        dialog.pack();
        dialog.setLocation(150, 150);
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }
}
