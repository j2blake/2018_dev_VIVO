<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Default individual search view -->

<#import "lib-vivo-properties.ftl" as p>

<a href="${individual.profileUrl}" title="${i18n().individual_name}">${individual.name}</a>

<@p.displayTitle individual />

<#if color??>
	<ul>
	<#list color as individualColor>
		<li>
    	<span class="display-title">Cover color: ${individualColor.color}</span>
    	</li>
    </#list>
    </ul>
</#if>

<p class="snippet">${individual.snippet}</p>
