goog.require('Blockly.Blocks');



//Blockly.Blocks.colour.HUE = 20;

/*Blockly.Blocks['test'] = {
  
  init: function() {
    this.jsonInit({
		  "type": "test",
		  "message0": "Stuff to say:  %1",
		  "args0": [
		    {
		      "type": "field_input",
		      "name": "STUFF",
		      "text": "Hi"
		    }
		  ],
		  "previousStatement": null,
		  "nextStatement": null,
		  "colour": 330,
		  "tooltip": "",
		  "helpUrl": ""
    });
  }
};

Blockly.Java['test'] = function(block) {
  var text_stuff = block.getFieldValue('STUFF');
  // TODO: Assemble JavaScript into code variable.
  var code = 'System.out.println("' + text_stuff + '");\n';
  return code;
}*/

/*

Blockly.Blocks['name'] = {
  
  init: function() {
    this.jsonInit({
      
    });
  }
};

*/


Blockly.Blocks['mcblock'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblock",
  "message0": "Minecraft Block %1 Name: %2 %3 Material: %4 %5 Options %6 %7",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "field_input",
      "name": "NAME",
      "text": "change_me"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_dropdown",
      "name": "MATERIAL",
      "options": [
        [
          "wood",
          "Material.wood"
        ],
        [
          "rock",
          "Material.rock"
        ]
      ]
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "Options",
      "check": "mcblockoptions_quantity"
    }
  ],
  "inputsInline": false,
  "previousStatement": "none",
  "nextStatement": "mcblock",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

function isJavaId(c)
{
  return c.match(/[a-zA-Z0-9]/i);
}

function make_java_id(name)
{
  var result = "";
  for (var i = 0; i < name.length; ++i) {
    var c = name.charAt(i);
    if (isJavaId(c)) {
      result = result + c;
    }
    else {
      result = result + "_";
    }
  }

  return result;
}


Blockly.Java['mcblock'] = function(block) {
  var value_name = make_java_id(block.getFieldValue('NAME'));
  var raw_value_name = block.getFieldValue('NAME');
  var dropdown_material = block.getFieldValue('MATERIAL');
  var statements_options = Blockly.Java.statementToCode(block, 'Options');
  // TODO: Assemble Java into code variable.
  var code = 
    '    public class Mcblock_' + value_name + ' extends BlockBase {\n' +
    '        public Mcblock_' + value_name + '() {\n' +
    '            super(BLOCK_ID, CREATIVE_TAB, "' + raw_value_name + '", ' + dropdown_material + '); \n' +
    '        }\n\n' +
          statements_options +
    '    }\n';

  return code;
};



Blockly.Blocks['mcblockoptions_quantity'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions_quantity",
  "message0": "Amount: %1",
  "args0": [
    {
      "type": "input_value",
      "name": "AMOUNT",
      "check": "Number"
    }
  ],
  "previousStatement": null,
  "nextStatement": null,
  "colour": 230,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcblockoptions_quantity'] = function(block) {
  var value_amount = Blockly.Java.valueToCode(block, 'AMOUNT', Blockly.Java.ORDER_ATOMIC);
  // TODO: Assemble Java into code variable.
  var code = 
  '    @Override\n' + 
  '    public int quantityDropped(Random r){\n' +
  '        return Math.max(0,(int)' + value_amount + ');\n' + 
  '    }\n';
  return code;
};