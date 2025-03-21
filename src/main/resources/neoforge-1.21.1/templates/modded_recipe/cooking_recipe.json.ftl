<#include "../mcitems.ftl">
{
  "type": "farmersdelight:cooking",
  "cookingtime": ${data.processing_time},
  "experience": ${data.experience},
  "ingredients": [
    <#assign ingredients = "">
    <#list data.ingredient as element>
        <#if element??>
            <#assign ingredients += "{${mappedMCItemToItemObjectJSON(element)}},">
        </#if>
    </#list>
    ${ingredients[0..(ingredients?last_index_of(',') - 1)]}
  ],
  "recipe_book_tab": ${data.list},
  "result": {
    "count": ${data.amount},
    "item": "${mappedMCItemToRegistryName(data.result)}"
  }
}
