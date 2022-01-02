<template>
    <div class="text-center">
        <h1>Cerrando session</h1>
    </div>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { onMounted } from "vue";

export default {
    name: "Logout",
    setup() {
        const store = useStore();
        const router = useRouter();
        store.dispatch("auth/logout");
        onMounted(() => router.push("/"));
    },
}
</script>