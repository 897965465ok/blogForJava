<template>
  <article class="container  ">
    <header class="header">
      header
    </header>
    <main class="main">
      <section class="text-gray-600 body-font relative">
        <div class="absolute inset-0 bg-gray-300">
          <iframe width="100%" height="100%" frameborder="0" marginheight="0" marginwidth="0" title="map" scrolling="no" src="https://maps.google.com/maps?width=100%&amp;height=600&amp;hl=en&amp;q=%C4%B0zmir+(My%20Business%20Name)&amp;ie=UTF8&amp;t=&amp;z=14&amp;iwloc=B&amp;output=embed" style="filter: grayscale(1) contrast(1.2) opacity(0.4);"></iframe>
        </div>
        <div class="container px-5 py-24 mx-auto flex">
          <div class="lg:w-1/3 md:w-1/2 bg-white rounded-lg p-8 flex flex-col md:ml-auto w-full mt-10 md:mt-0 relative z-10 shadow-md">
            <h2 class="text-gray-900 text-lg mb-1 font-medium title-font">Feedback</h2>
            <p class="leading-relaxed mb-5 text-gray-600">Post-ironic portland shabby chic echo park, banjo fashion axe</p>
            <div class="relative mb-4">
              <label for="email" class="leading-7 text-sm text-gray-600">Email</label>
              <input type="email" id="email" name="email" class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out">
            </div>
            <div class="relative mb-4">
              <label for="message" class="leading-7 text-sm text-gray-600">Message</label>
              <textarea id="message" name="message" class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 h-32 text-base outline-none text-gray-700 py-1 px-3 resize-none leading-6 transition-colors duration-200 ease-in-out"></textarea>
            </div>
            <button class="text-white bg-indigo-500 border-0 py-2 px-6 focus:outline-none hover:bg-indigo-600 rounded text-lg">Button</button>
            <p class="text-xs text-gray-500 mt-3">Chicharrones blog helvetica normcore iceland tousled brook viral artisan.</p>
          </div>
        </div>
      </section>
    </main>
    <aside class="aside">
      aside
    </aside>
    <footer class="footer">footer</footer>
  </article>
</template>

<script>
export default {
  name: "index"
}
</script>

<style lang="scss" scoped>
.container {
  display: grid;
  // 区域一个名称算一个单元
  grid-template-areas:'header header'
                      'main aside'
                     'footer footer';
  //grid-template-columns: 200px 1fr 2fr;
  //grid-template-rows: minmax(100px,200px) repeat(5 ,1fr);
  //grid-gap:10px;
  height: 700px;
  width: 100%;
  //控制横线
  grid-template-rows: 15% 75% 15%;
  // 控制纵线
  grid-template-columns: 70% 30%;
  // 控制线大小
  grid-column-gap: 5px;
  grid-row-gap: 5px;


  // 横排还是纵排
  grid-auto-flow: column;
  // 设置多余的单元
  grid-auto-rows: 50px;

  grid-column-start: span 2;

  .item-1 {
    grid-column: 1 / 3;
    grid-row: 1 / 2;
  }
  .header {
    grid-area: header;

  }

  .main {
    grid-area: main;
  }

  .aside {
    grid-area: aside;
  }

  .footer {
    grid-area: footer;
  }
}
</style>
