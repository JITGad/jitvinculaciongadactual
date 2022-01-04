<template>
  <slot v-if="IsAutorized"></slot>
</template>

<script>
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';
export default {
  props: {
    roles: {
      type: String,
      required: true,
    },
  },
  setup(props, context) {
      const Store = useStore();
      const IsAutorized = computed(() => {
          const myRoles = props.roles.split(',');
          return myRoles.includes(Store.state.auth.user.rol)
      });
      return {
          IsAutorized
      }
  },
};
</script>