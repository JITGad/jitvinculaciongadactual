<template>
  <a @click="handleClick">
    <i :class="icon"></i>
  </a>
</template>

<script>
export default {
    name: "MyLinkEdit",
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
            FullUrl,
            handleClick
        }
    },
}
</script>