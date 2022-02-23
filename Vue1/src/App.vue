<template>
  <div id="app">
    <div style="margin-top: 20px">
      <el-input v-model="name" prefix-icon="el-icon-search"
                placeholder="输入名称进行查询"
                style="float:left;width: 250px;"
                clearable @change="selectAll"/>
      <el-button  @click="searchPerson()" style="float: left;margin-left:20px">查询</el-button>

      <el-button type="primary" @click="dialogVisible = true,empty()" style="margin-left: 50px">添加</el-button>
    </div>
    <el-table :data="tableData">
      <el-table-column
          label="序号"
          width="70px">
        <template slot-scope="scope">
          {{scope.$index+1}}
        </template>
      </el-table-column>        //人为增加一列
      <el-table-column label="id" prop="id" width="auto"></el-table-column>
      <el-table-column label="用户名" prop="name" width="auto"></el-table-column>
      <el-table-column label="年龄" prop="age" width=auto></el-table-column>
      <el-table-column label="操作" align="center" min-width="100">
        　　　　<template slot-scope="scope">				　　　　　
        　　　　　　<el-button type="warning" @click="dialogVisible2 = true, modifyPerson(scope.row.id)">修改</el-button>
        　　　　　　<el-button type="danger" @click="deletePerson(scope.row.id)">删除</el-button>
        　　　　</template>
      </el-table-column>

    </el-table>



    <el-dialog
        title="Add"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
      <el-form  label-width="80px" :model="formLabelAlign">
        <el-form-item label="ID">
          <el-input v-model="formLabelAlign.id"></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="formLabelAlign.name"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="formLabelAlign.age"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false ">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false ,addPerson()">确 定</el-button>
  </span>
    </el-dialog>


    <el-dialog
        title="Edit"
        :visible.sync="dialogVisible2"
        width="30%"
        :before-close="handleClose">
      <el-form  label-width="80px" :model="formLabelAlign">
        <el-form-item label="ID">
          <el-input v-model="formLabelAlign.id" readonly></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="formLabelAlign.name"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="formLabelAlign.age"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible2 = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible2 = false ,editPerson()">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      tableData:'',
      name:'',
      dialogVisible: false,
      dialogVisible2: false,
      formLabelAlign: {
        id: '',
        name: '',
        age: ''
      },
      readonly:true
    }
  },
  methods: {
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
            this.empty();
          })
          .catch(_ => {});
    },

    empty(){
      this.formLabelAlign.id='';
      this.formLabelAlign.name='';
      this.formLabelAlign.age='';
    },

    editPerson(){
      axios.post('http://localhost:8089/person/edit',this.formLabelAlign).then(res=>{
        console.log(res.data)
        this.selectAll()
      })
    },

    addPerson(){
      axios.post('http://localhost:8089/person/add',this.formLabelAlign).then(res=>{
        this.selectAll()
      })
    },


    selectAll() {   //获取所有数据
      axios.get('http://localhost:8089/person/findAll').then(res => {

        this.tableData = res.data
      })
    },
    modifyPerson(id){
      axios.get('http://localhost:8089/person/findById?id='+id).then(res=>{
        this.formLabelAlign=res.data
      })
    }
    ,
    deletePerson(id) {   //删除所有数据
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.delete('http://localhost:8089/person/deletePerson?id='+id).then(res => {
          this.selectAll()
        })
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    },
    searchPerson() {  //根据名称模糊查询
      axios.post('http://localhost:8089/person/findByNameLike?name='+ this.name).then(res => {
        console.log("okk!")
        if (res.data == null)
          return this.$message.error("没有符合条件的记录")
        this.$message.success('查询完成')
        this.tableData = res.data
      })
    }
  },
  mounted() {
    this.selectAll()
  }
}
</script>

<style scoped>  /* 下面的CSS样式仅仅作用于当前组件的Element元素 */
/deep/ .el-table .cell {
  text-align: center;
}
</style>