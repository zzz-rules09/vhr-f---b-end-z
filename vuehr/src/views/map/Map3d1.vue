<template>
    <div>
     <div>
     <el-button icon="el-icon-plus" size="small" type="primary"
    @click="showAddMap">添加</el-button>
     </div>
     <div class="mapMain">
     <el-table :data="maps" border @selection-change="handleSelectionChange" size="small" stripe style="width:
     70%">
     <el-table-column type="selection" width="55"></el-table-column>
     <el-table-column prop="id" label="编号" width="55"></el-table-column>
     <el-table-column prop="name" label="地区" width="180">
    </el-table-column>
     <el-table-column prop="keyword" width="150" label="坐标">
    </el-table-column>
     <!-- <el-table-column prop="createDate" width="150" label="创建时间"></el-table-column> -->
     <el-table-column label="操作">
     <template slot-scope="scope">
     <el-button size="mini"
    @click="showLookView(scope.$index, scope.row)">预览 </el-button>
     <el-button size="mini" type="danger"
    @click="handleDelete(scope.$index,scope.row)">删除</el-button>
     </template>
     </el-table-column>
     </el-table>
     <el-button @click="deleteMany" type="danger" size="small"
    style="margin-top: 8px" :disabled="multipleSelection.length==0">批量删除</el-button>
     </div>
     
     <el-dialog title="地图展示" :visible.sync="dialogVisible_show" width="80%">
        <el-row     :gutter="20">
     <el-container>
     <el-aside style="width: 100%;">
     <vl-map :load-tiles-while-animating="true"
    :load-tiles-while-interacting="true" style="height: 800px;" data-projection="EPSG:4326">
     <vl-view :zoom.sync="location.zoom"
    :center.sync="location.center" :rotation.sync="location.rotation">
    </vl-view>
     <vl-layer-tile id="bingmaps">
     <vl-source-bingmaps :api-key="location.apiKey" :imagery-set="location.imagerySet"></vl-source-bingmaps>
     </vl-layer-tile>
     </vl-map>
     </el-aside>
     
     </el-container>
     </el-row>
     <span slot="footer" class="dialog-footer">
     <el-button size="small"
    @click="dialogVisible_show=false"> 退 出 </el-button>
     </span>
     </el-dialog>
    
    <!--
    <el-dialog
      title="地图展示"
      :visible.sync="dialogVisible_show"
      width="90%"
      height="100%"
    >
      <iframe src="http://localhost:4111/index.html#" frameborder="0" width="100%" height="600px"></iframe>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible_show=false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible_show=false">确 定</el-button>
      </span>
    </el-dialog>
    -->
     <el-dialog title="增加地图" :visible.sync="dialogVisible_add"
    width="90%">
     <div>
     <div>
     <el-tag>地图名称</el-tag>
     <el-input class="addMapInput" size="small" v-model="AddMap.name"></el-input>
     </div>
     <div>
     <el-tag>地图坐标</el-tag>
     <el-input class="addMapInput" size="small" v-model="AddMap.keyword">
     </el-input>
     </div>
     </div>
     <iframe src="http://localhost:4111/newKey.html#" frameborder="0" width="100%" height="600px"></iframe>
     <span slot="footer" class="dialog-footer">
     <el-button size="small" @click="dialogVisible_add =
    false"> 取消 </el-button>
     <el-button size="small" type="primary" @click="addMap">
    确定 </el-button>
     </span>
     </el-dialog>
    </div>
    </template>
    <script>
     import { ModelGltf } from 'vue-3d-model';
     import { BingmapsSource } from 'vuelayers'
     //import 'vuelayers/lib/style.css'
     export default {
     name: "manage",
     components: { ModelGltf },
     data() {
     return {
     m: {
     name: '',
     keyword: ''
     },
     dialogVisible_show: false,
     dialogVisible_add: false,
     loading: false,
     AddMap: {
     name: '',
     keyword: ''
     },
     multipleSelection: [],
     maps: [],
     location: {
     zoom: 15,
     center: [0, 0],
     rotation: 0,
     apiKey: 'Au9zjPDr51o7DJuRc9gemyp1zu-xS4P5XJ4wU50CLXoOVM3gKR31DhwsQsRQSmV0',
     imagerySet: 'AerialWithLabels',
     }
     }
     },
     mounted() {
     this.initMaps();
     },
     methods: {
     deleteMany() {
     this.$confirm(' 此 操 作 将 永 久 删 除【' +
    this.multipleSelection.length + '】条记录, 是否继续?', '提示', {
    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'}
     ).then(() => {
     let ids = '?';
     this.multipleSelection.forEach(item => {
     ids += 'ids=' + item.id + '&';
     })
     this.deleteRequest("/map/manage/" +
    ids).then(resp => {
     if (resp) { this.initMaps(); } })
     }).catch(() => {
     this.$message({ type: 'info', message: '已取消删除' });
     });
     },
     handleSelectionChange(val) {
     this.multipleSelection = val;
     },
     showAddMap() {
     this.AddMap.name = '';
     this.AddMap.keyword = '';
     this.dialogVisible_add = true;
     },
     addMap() {
     this.postRequest("/map/manage/",
    this.AddMap).then(resp => {
     if (resp) {
     this.initMaps();
     this.dialogVisible_add = false; }
     })
     },
     showLookView(index, data) {
     // Object.assign(this.m, data);
     let ss = data.keyword.split(',');
     this.location.center = [parseFloat(ss[0]),
    parseFloat(ss[1])];
     this.dialogVisible_show = true;
     },
     handleDelete(index, data) {
     this.$confirm('此操作将永久删除【' + data.name + '】, 是否 继续 ? ','提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'}
     ).then(() => {
     
    this.deleteRequest("/map/manage/"+data.id).then(resp => {
     if (resp) { this.initMaps(); }
     })
     }).catch(() => {
     this.$message({ type: 'info', message: '已取消删除' });
     });
     },
     initMaps() {
     this.loading = true;
     this.getRequest("/map/manage/").then(resp => {
    this.loading = false; if (resp) { this.maps = resp; } })
     }
     }
     }
    </script>
    <style>
     .addMapInput { width: 200px; margin-left: 8px; } 
     .mapMain { margin-top: 10px; } .map-img {
     width: 600px;
     height: 600px;
     border-radius: 72px; }
     .img-container {
     width: 900px; height: 900px; /*width: 150px;*/ /*height:
    120px;*/
     display: flex;
     justify-content: center;
     }
     .bm-view { width: 100%; height: 300px; }
     .map2d {height: 100%}
    </style>