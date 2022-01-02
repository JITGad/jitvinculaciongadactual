<template>
  <main-layout>
    <div class="mb-3" style="text-align: center">
      <h3>{{ Title }}</h3>
    </div>
    <my-form @submit="handleSubmit">
      <router-view />
      <div v-if="!Loading" class="mb-3">
        <my-button
          class="btn btn-success"
          label="Guardar"
          :loading="Sending"
          @click="clickNormal"
        />
        <my-button
          v-if="!Edit"
          class="btn btn-info"
          label="Guardar y añadir otro"
          :loading="Sending"
          @click="clickNext"
        />
        <my-button
          class="btn btn-danger"
          label="Regresar"
          :loading="Sending"
          type="button"
          @click="Next"
        />
      </div>
    </my-form>
  </main-layout>
</template>

<script>
import { ref, provide, reactive } from "vue";
import { useRouter } from "vue-router";
import { message_error } from "../util/Messages.js";

export default {
  name: "Edit",
  setup(props, context) {
    const Title = ref("");
    const Sending = ref(false);
    const Loading = ref(false);
    const Edit = ref(false);
    const IsNext = ref(false);
    const UrlNext = ref("");
    const Router = useRouter();
    const state = reactive({
      child: {},
    });
    provide("layout", {
      bind,
      unbind,
      loading
    });
    function bind(component) {
      Title.value = component.title;
      UrlNext.value = component["url-next"];
      Edit.value = component["is-edit"];
      state.child = component;
    }
    function unbind(uid) {
      const index = state.child.uid;
      if (index == uid) {
        state.childs = {};
      }
    }
    function loading(val){
      Loading.value = val;
    }
    const handleSubmit = async function () {
      Sending.value = true;
      const response = await state.child.submit();
      Sending.value = false;
      if (!response.status.error) {
        if (IsNext.value) {
          Next();
          return;
        }
        state.child.clear();
      } else {
        message_error(response.status.message);
      }
    };
    const clickNormal = () => (IsNext.value = true);
    const clickNext = () => (IsNext.value = false);
    const Next = () => Router.push(UrlNext.value);
    return {
      Title,
      Edit,
      UrlNext,
      handleSubmit,
      clickNormal,
      clickNext,
      Next,
      Sending,
      Loading,
    };
  },
};
</script>