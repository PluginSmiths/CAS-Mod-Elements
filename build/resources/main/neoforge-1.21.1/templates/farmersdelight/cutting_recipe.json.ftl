<#include "../mcitems.ftl">
{
  "type": "farmersdelight:cutting",
  "ingredients": [
    {
      "item": "minecraft:dirt"
    }
  ],
  "result": [
    {
      "chance": 0,
      "count": 0,
      "item": "minecraft:dirt"
    }
  ],
  "sound": ${data.sound},
  "tool": {
    "type": "farmersdelight:tool_action",
    "action": "${data.list}"
  }
}
