<template><div>
<iframe src="http://localhost:4111/index.html#" frameborder="0" width="100%" height="600px"></iframe>
    </div></template>

<script>
export default {
    name: "indexJK",
    data() {
      return {
        name: "indexJK", 
        dialogVisible_show: false, //弹出框
      };
    },
    methods: {
      test() {
        this.dialogVisible_show = true;
      }
    }
  };
  </script>