<#function getBackgroundResourceLocation namespace path>
    <#if namespace == "minecraft">
        <#return 'new ResourceLocation("minecraft:textures/${path}.png")'>
    </#if>
    <#return 'new ResourceLocation("${modid}" ,"textures/${path}.png")'>
</#function>

<#function getBoolean val>
    <#if val == "TRUE">
        <#return true>
    </#if>
    <#return false>
</#function>

<#function removeGlobal varName>
    <#return '${varName?replace("global:", "")}'>
</#function>

<#function getGlobalVar varName>
    <#return '${JavaModName}Variables.${removeGlobal(varName)}'>
</#function>

<#function isComponentEmpty component>
    <#if component == 'Component.literal("")' || component == 'Component.translatable("")'>
        <#return true>
    </#if>
    <#return false>
</#function>

<#function getTooltip component>
    <#if isComponentEmpty(component)>
        <#return ''>
    </#if>
    <#return '.setTooltip(${component})'>
</#function>