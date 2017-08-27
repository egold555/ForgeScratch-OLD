goog.require('Blockly.Blocks');



//Blockly.Blocks.colour.HUE = 20;

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
  "message0": "Minecraft Block %1 Name: %2 %3 Properties: %4 %5 Options %6 %7",
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
          "Dirt",
          "Material.ground"
        ],
        [
          "Stone",
          "Material.rock"
        ],
        [
          "Wood",
          "Material.wood"
        ],
        [
          "Glass",
          "Material.glass"
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
      "check": "mcblockoptions"
    }
  ],
  "inputsInline": false,
  "colour": 315,
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
      "type": "mcblockoptions",
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
  "colour": 330,
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



Blockly.Blocks['mcblockoptions_lightopacity'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions",
  "message0": "Light Opacity %1",
  "args0": [
    {
      "type": "input_value",
      "name": "LIGHT_OPACITY",
      "check": "Number"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};



Blockly.Blocks['mcblockoptions_lightvalue'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions",
  "message0": "Light Value %1",
  "args0": [
    {
      "type": "input_value",
      "name": "LIGHT_VALUE",
      "check": "Number"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
}; 


Blockly.Blocks['mcblockoptions_click_right'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions",
  "message0": "On Right Click %1 %2",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "code"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
}; 

Blockly.Blocks['mcblockoptions_click_left'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions",
  "message0": "On Left Click %1 %2",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "CODE"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
}; 


Blockly.Blocks['mcblockoptions_blockplaced'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions",
  "message0": "On Block Placed %1 %2",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "CODE"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
}; 



Blockly.Blocks['mcblockoptions_block_broken_player'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions",
  "message0": "On Block Mined %1 %2",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "CODE"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
}; 


Blockly.Blocks['mcblockoptions_block_broken_explosion'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions",
  "message0": "On Block Destroyed By Explosion %1 %2",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "NAME"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
}; 


Blockly.Blocks['mcblockoptions_walkthrough'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions",
  "message0": "On Block Walkthough %1 %2",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "CODE"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
}; 


Blockly.Blocks['mcblockoptions_transparent'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions",
  "message0": "Render Block Like Glass",
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
}; 


Blockly.Blocks['mcblockoptions_experience'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions",
  "message0": "Amount Of XP To Drop %1",
  "args0": [
    {
      "type": "input_value",
      "name": "AMOUNT",
      "check": "Number"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
}; 