function simpleRepeatingInputMixin(mutatorContainer, mutatorInput, inputName, inputProvider, isProperInput = true,
        fieldNames = [], disableIfEmpty) {
    return {
        // Store number of inputs in XML as '<mutation inputs="inputCount_"></mutation>'
        mutationToDom: function () {
            var container = document.createElement('mutation');
            container.setAttribute('inputs', this.inputCount_);
            return container;
        },

        // Retrieve number of inputs from XML
        domToMutation: function (xmlElement) {
            this.inputCount_ = parseInt(xmlElement.getAttribute('inputs'), 10);
            this.updateShape_();
        },

        // Store number of inputs in JSON
        saveExtraState: function () {
            return {
                'inputCount': this.inputCount_
            };
        },

        // Retrieve number of inputs from JSON
        loadExtraState: function (state) {
            this.inputCount_ = state['inputCount'];
            this.updateShape_();
        },

        // "Split" this block into the correct number of inputs in the mutator UI
        decompose: function (workspace) {
            const containerBlock = workspace.newBlock(mutatorContainer);
            containerBlock.initSvg();
            var connection = containerBlock.getInput('STACK').connection;
            for (let i = 0; i < this.inputCount_; i++) {
                const inputBlock = workspace.newBlock(mutatorInput);
                inputBlock.initSvg();
                connection.connect(inputBlock.previousConnection);
                connection = inputBlock.nextConnection;
            }
            return containerBlock;
        },

        // Rebuild this block based on the number of inputs in the mutator UI
        compose: function (containerBlock) {
            let inputBlock = containerBlock.getInputTargetBlock('STACK');
            // Count number of inputs.
            const connections = [];
            const fieldValues = [];
            while (inputBlock && !inputBlock.isInsertionMarker()) {
                connections.push(inputBlock.valueConnection_);
                fieldValues.push(inputBlock.fieldValues_);
                inputBlock = inputBlock.nextConnection && inputBlock.nextConnection.targetBlock();
            }
            // Disconnect any children that don't belong. This is skipped if the provided input is a dummy input
            if (isProperInput) {
                for (let i = 0; i < this.inputCount_; i++) {
                    const connection = this.getInput(inputName + i) && this.getInput(inputName + i).connection.targetConnection;
                    if (connection && connections.indexOf(connection) == -1) {
                        connection.disconnect();
                    }
                }
            }
            this.inputCount_ = connections.length;
            this.updateShape_();
            // Reconnect any child blocks and update the field values
            for (let i = 0; i < this.inputCount_; i++) {
                if (isProperInput) {
                    Blockly.Mutator.reconnect(connections[i], this, inputName + i);
                }
                if (fieldValues[i]) {
                    for (let j = 0; j < fieldNames.length; j++) {
                        // If this is a new field, then keep its initial value, otherwise assign the stored value
                        if (fieldValues[i][j] != null)
                            this.getField(fieldNames[j] + i).setValue(fieldValues[i][j]);
                    }
                }
            }
        },

        // Keep track of the connected blocks, so that they don't get disconnected whenever an input is added or moved
        // This also keeps track of the field values
        saveConnections: function (containerBlock) {
            let inputBlock = containerBlock.getInputTargetBlock('STACK');
            let i = 0;
            while (inputBlock) {
                if (!inputBlock.isInsertionMarker()) {
                    const input = this.getInput(inputName + i);
                    if (input) {
                        if (isProperInput) {
                            inputBlock.valueConnection_ = input.connection.targetConnection;
                        }
                        inputBlock.fieldValues_ = [];
                        for (let j = 0; j < fieldNames.length; j++) {
                            const currentFieldName = fieldNames[j] + i;
                            inputBlock.fieldValues_[j] = this.getFieldValue(currentFieldName);
                        }
                    }
                    i++;
                }
                inputBlock = inputBlock.getNextBlock();
            }
        },

        // Add/remove inputs from this block
        updateShape_: function () {
            this.handleEmptyInput_(disableIfEmpty);
            // Add proper inputs
            for (let i = 0; i < this.inputCount_; i++) {
                if (!this.getInput(inputName + i))
                    inputProvider(this, inputName, i);
            }
            // Remove extra inputs
            for (let i = this.inputCount_; this.getInput(inputName + i); i++) {
                this.removeInput(inputName + i);
            }
        },

        // Handle the dummy "empty" input or warning for when there are no proper inputs
        handleEmptyInput_: function (disableIfEmpty) {
            if (disableIfEmpty === undefined) {
                if (this.inputCount_ && this.getInput('EMPTY')) {
                    this.removeInput('EMPTY');
                } else if (!this.inputCount_ && !this.getInput('EMPTY')) {
                    this.appendDummyInput('EMPTY').appendField(javabridge.t('blockly.block.' + this.type + '.empty'));
                }
            } else if (disableIfEmpty) {
                this.setWarningText(this.inputCount_ ? null : javabridge.t('blockly.block.' + this.type + '.empty'));
                this.setEnabled(this.inputCount_);
            }
        }
    }
}

function registerSimpleMutatorContainer (blockId, localizationKey, colour) {
    Blockly.Blocks[blockId] = {
        init: function () {
            this.appendDummyInput().appendField(javabridge.t(localizationKey));
            this.appendStatementInput('STACK');
            this.contextMenu = false;
            this.setColour(colour);
        }
    };
}

function registerSimpleMutatorInput (blockId, localizationKey, colour) {
    Blockly.Blocks[blockId] = {
        init: function () {
            this.appendDummyInput().appendField(javabridge.t(localizationKey));
            this.setPreviousStatement(true);
            this.setNextStatement(true);
            this.contextMenu = false;
            this.setColour(colour);
        }
    };
}


// Mutators and Extensions
registerSimpleMutatorContainer('requirement_is_value_mutator_container', 'blockly.block.requirement_is_value_mutator.container', "%{BKY_CONFIG_REQUIREMENTS_HUE}");
registerSimpleMutatorInput('requirement_is_value_mutator_input', 'blockly.block.requirement_is_value_mutator.input', "%{BKY_CONFIG_REQUIREMENTS_HUE}");

registerSimpleMutatorContainer('requirements_mutator_container', 'blockly.block.requirements_mutator.container', "%{BKY_CONFIG_REQUIREMENTS_HUE}");
registerSimpleMutatorInput('requirements_mutator_input', 'blockly.block.requirements_mutator.input', "%{BKY_CONFIG_REQUIREMENTS_HUE}");

Blockly.Extensions.registerMutator("requirement_is_value_mutator", simpleRepeatingInputMixin("requirement_is_value_mutator_container",
    "requirement_is_value_mutator_input", "target",
    function (thisBlock, inputName, index) {
        thisBlock.appendValueInput(inputName + index).setAlign(Blockly.Input.Align.RIGHT);
    }), undefined, ["requirement_is_value_mutator_input"]);

Blockly.Extensions.registerMutator("requirements_mutator", simpleRepeatingInputMixin("requirements_mutator_container",
    "requirements_mutator_input", "target",
    function (thisBlock, inputName, index) {
        thisBlock.appendValueInput(inputName + index).setCheck("Requirement").setAlign(Blockly.Input.Align.RIGHT);
    }), undefined, ["requirements_mutator_input"]);


Blockly.Extensions.register("config_entries_variables", function () {
    let variables = Array.prototype.concat(getVariablesOfType("BooleanListEntry"),getVariablesOfType("StringListEntry"),
                                           getVariablesOfType("DoubleListEntry"), getVariablesOfType("IntegerListEntry"),
                                           getVariablesOfType("IntegerSliderEntry"));

    this.getInput("entry").appendField(new Blockly.FieldDropdown(variables), "ENTRY");
});


// Blocks
Blockly.Blocks["config_start"] = {
    init: function () {
        this.appendDummyInput().appendField(javabridge.t("blockly.block.config_start"));
        this.setStyle("hat_blocks");
        this.setNextStatement(true);
        this.setColour(120);
        this.setTooltip(javabridge.t("blockly.block.config_start.tooltip"));
    }
}

Blockly.Blocks["component_from_text"] = {
    init: function () {
        this.appendValueInput("text").appendField(javabridge.t("blockly.block.component_from_text"));
        this.setInputsInline(true);
        this.setPreviousStatement(false);
        this.setNextStatement(false);
        this.setOutput(true, "Component");
        this.setColour("%{BKY_CONFIG_TEXT_COMPONENTS_HUE}");
    }
}

Blockly.Blocks["component_from_localized_text"] = {
    init: function () {
        this.appendValueInput("key").appendField(javabridge.t("blockly.block.component_from_localized_text"));
        this.setInputsInline(true);
        this.setPreviousStatement(false);
        this.setNextStatement(false);
        this.setOutput(true, "Component");
        this.setColour("%{BKY_CONFIG_TEXT_COMPONENTS_HUE}");
    }
}


// Categories color
Blockly.Msg.CONFIG_SCREEN_CATEGORY_HUE = "#0279a1"
Blockly.Msg.CONFIG_CATEGORIES_CATEGORY_HUE = "#422601" // "#1d3e47" // #01698c
Blockly.Msg.CONFIG_ENTRIES_CATEGORY_HUE = "#01475e"
Blockly.Msg.CONFIG_UTILS_CATEGORY_HUE = "#38705d"

// Blocks color
Blockly.Msg.CONFIG_TEXT_COMPONENTS_HUE = Blockly.Msg.CONFIG_UTILS_CATEGORY_HUE
Blockly.Msg.CONFIG_SETTINGS_HUE = Blockly.Msg.CONFIG_SCREEN_CATEGORY_HUE
Blockly.Msg.CONFIG_CATEGORY_HUE = Blockly.Msg.CONFIG_CATEGORIES_CATEGORY_HUE
Blockly.Msg.CONFIG_SUBCATEGORY_HUE = "#4a3e01" // #075670
Blockly.Msg.CONFIG_BOOLEAN_HUE = "#466585"
Blockly.Msg.CONFIG_STRING_HUE = "#1f8f69"
Blockly.Msg.CONFIG_REQUIREMENTS_HUE = "#1a4033"
Blockly.Msg.CONFIG_DOUBLE_HUE = "#454e80"
Blockly.Msg.CONFIG_INTEGER_HUE = "#2f3866"
Blockly.Msg.CONFIG_SLIDER_HUE = "#17256e"