--##Rollback Queries

--select * from ADC_FACILITY_EFORM_TYPE_MAPPING where META_ENT_ID = '2202000149'
delete from ADC_FACILITY_EFORM_TYPE_MAPPING where META_ENT_ID = '2202000149' --[DELETE]

--select * from WF_PROCESS_PARTICIPANT where PROCESS_ALLOC_ID = '200202'
delete WF_PROCESS_PARTICIPANT where PROCESS_ALLOC_ID = '200202' --[DELETE]

--select * from WF_PROCESS_ENTITY_ALLOC where PROCESS_ALLOC_ID = '200202' 
delete from WF_PROCESS_ENTITY_ALLOC where PROCESS_ALLOC_ID = '200202' --[DELETE]

--select * from META_ENTITY_CATEGORY where META_ENT_ID = '2202000149' 
delete from META_ENTITY_CATEGORY where META_ENT_ID = '2202000149' --[DELETE]

--select * from VALUE_TREE_NODE where  TREE_NODE_ID = '2000000200' --[Roshan Account]
--delete from VALUE_TREE_NODE where  TREE_NODE_ID = '2000000200' --[DELETE]

--select * from META_VIEW_PARAM where META_VIEW_ID = '2202000149_002' 
delete from META_VIEW_PARAM where META_VIEW_ID = '2202000149_002' --[DELETE]

--select * from META_ENTITY_RELATION where MAIN_ENTITY_ID = '2202000149'
delete from META_ENTITY_RELATION where MAIN_ENTITY_ID = '2202000149' --[DELETE]

--select * from META_VIEW_RELATIONSHIP where PARENT_VIEW_ID = '2202000149_001' 
delete from META_VIEW_RELATIONSHIP where PARENT_VIEW_ID = '2202000149_001' --[DELETE] 

--select * from META_VIEW_ATTRIB where META_ENT_ID = '2202000149'
delete from META_VIEW_ATTRIB where META_ENT_ID = '2202000149' --[DELETE]

--select * from META_VIEW where META_ENT_ID = '2202000149'
delete from META_VIEW where META_ENT_ID = '2202000149' --[DELETE]

--select * from META_ENTITY_ATTRIB where META_ENT_ID = '2202000149'
delete from META_ENTITY_ATTRIB where META_ENT_ID = '2202000149' --[DELETE]

--select * from META_ENTITY where META_ENT_ID = '2202000149' 
delete from META_ENTITY where META_ENT_ID = '2202000149' --[DELETE]