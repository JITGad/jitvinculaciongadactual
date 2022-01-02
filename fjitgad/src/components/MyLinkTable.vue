<template>
  <a @click="handleClick" style="cursor: pointer;">
    <i :class="icon"></i>
  </a>
</template>

<script>
export default {
    name: "MyLinkTable",
    emits:['click'],
    props: {
        object: {
            type: Object,
            required: true
        },
        icon: {
            type: String,
            required: true
        }
    },
    setup(props, context) {
        const handleClick = () =>{
            context.emit('click', props.object);
        }
        return {
            handleClick
        }
    },
}
</script>