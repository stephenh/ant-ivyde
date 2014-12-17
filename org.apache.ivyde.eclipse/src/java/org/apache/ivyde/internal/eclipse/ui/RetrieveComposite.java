/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.apache.ivyde.internal.eclipse.ui;

import java.util.Arrays;

import org.apache.ivy.core.retrieve.RetrieveOptions;
import org.apache.ivyde.eclipse.cp.RetrieveSetup;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class RetrieveComposite extends Composite {

    private static final String[] OVERWRITE_MODES = new String[] { 
        RetrieveOptions.OVERWRITEMODE_NEWER,
        RetrieveOptions.OVERWRITEMODE_DIFFERENT,
        RetrieveOptions.OVERWRITEMODE_ALWAYS,
        RetrieveOptions.OVERWRITEMODE_NEVER };

    public static final String TOOLTIP_RETRIEVE_PATTERN = "Exemple: lib/[conf]/[artifact].[ext]\n"
            + "To copy artifacts in folder named lib without revision by folder"
            + " named like configurations";

    public static final String TOOLTIP_RETRIEVE_CONFS = "Comma separated list of configuration to"
            + " retrieve\nExemple: '*' or 'compile,test'";

    public static final String TOOLTIP_RETRIEVE_TYPES = "Comma separated list of types to retrieve"
            + "\nExemple: '*' or 'jar,source'";

    private static final String DEFAULT_PATTERN = "/[type]s/[artifact]-[revision](-[classifier]).[ext]";

    private PathEditor retrievePatternText;

    private Button retrieveSyncButton;

    private Button retrieveSymlinkButton;

    private Label overwriteModeLabel;

    private Combo overwriteModeCombo;

    private Text confsText;

    private Text typesText;

    public RetrieveComposite(Composite parent, int style, boolean withConf, IProject project) {
        super(parent, style);
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        setLayout(layout);

        retrievePatternText = new PathEditor(this, SWT.NONE, "Retrieve pattern", project, null) {
            private Button addPattern;

            protected boolean addButtons(Composite buttons) {
                addPattern = new Button(buttons, SWT.NONE);
                addPattern.setLayoutData(new GridData(GridData.END, GridData.CENTER, true, false));
                addPattern.setText("Add def. pattern");
                addPattern.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent e) {
                        getText().setText(getText().getText() + DEFAULT_PATTERN);
                    }
                });
                return true;
            }

            public void setEnabled(boolean enabled) {
                super.setEnabled(enabled);
                addPattern.setEnabled(enabled);
            }
        };
        retrievePatternText.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false,
                2, 1));
        retrievePatternText.setToolTipText(TOOLTIP_RETRIEVE_PATTERN);

        retrieveSyncButton = new Button(this, SWT.CHECK);
        retrieveSyncButton.setText("Delete old retrieved artifacts");
        retrieveSyncButton.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false, 2, 1));
        
        retrieveSymlinkButton = new Button(this, SWT.CHECK);
        retrieveSymlinkButton.setText("Make symlinks");
        retrieveSymlinkButton.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false, 2, 1));

        overwriteModeLabel = new Label(this, SWT.NONE);
        overwriteModeLabel.setText("Overwrite mode:");

        overwriteModeCombo = new Combo(this, SWT.READ_ONLY);
        overwriteModeCombo.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
        overwriteModeCombo.setToolTipText("Overwrite mode");
        for (int i = 0; i < OVERWRITE_MODES.length; i++) {
            overwriteModeCombo.add(OVERWRITE_MODES[i]);
        }

        if (withConf) {
            Label label = new Label(this, SWT.NONE);
            label.setText("Configurations:");

            confsText = new Text(this, SWT.SINGLE | SWT.BORDER);
            confsText.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
            confsText.setToolTipText(TOOLTIP_RETRIEVE_CONFS);
        }

        Label label = new Label(this, SWT.NONE);
        label.setText("Types:");

        typesText = new Text(this, SWT.SINGLE | SWT.BORDER);
        typesText.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
        typesText.setToolTipText(TOOLTIP_RETRIEVE_TYPES);

    }

    public RetrieveSetup getRetrieveSetup() {
        RetrieveSetup setup = new RetrieveSetup();
        setup.setRetrieveSync(retrieveSyncButton.getSelection());
        setup.setMakeSymLinks(retrieveSymlinkButton.getSelection());
        setup.setRetrievePattern(retrievePatternText.getText().getText());
        setup.setOverrideMode(OVERWRITE_MODES[Math.min(overwriteModeCombo.getSelectionIndex(), OVERWRITE_MODES.length)]);
        if (confsText != null) {
            setup.setRetrieveConfs(confsText.getText());
        }
        setup.setRetrieveTypes(typesText.getText());
        return setup;
    }

    public void init(RetrieveSetup setup) {
        retrievePatternText.getText().setText(setup.getRetrievePattern());
        retrieveSyncButton.setSelection(setup.isRetrieveSync());
        retrieveSymlinkButton.setSelection(setup.isMakeSymLinks());
        overwriteModeCombo.select(Math.max(Arrays.asList(OVERWRITE_MODES).indexOf(setup.getOverrideMode()), 0));
        if (confsText != null) {
            confsText.setText(setup.getRetrieveConfs());
        }
        typesText.setText(setup.getRetrieveTypes());
        setEnabled(true);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        retrievePatternText.setEnabled(enabled);
        retrieveSyncButton.setEnabled(enabled);
        retrieveSymlinkButton.setEnabled(enabled);
        overwriteModeLabel.setEnabled(enabled);
        overwriteModeCombo.setEnabled(enabled);
        if (confsText != null) {
            confsText.setEnabled(enabled);
        }
        typesText.setEnabled(enabled);
    }
}
