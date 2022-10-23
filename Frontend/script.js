// header("Access-Control-Allow-Origin: *");

var app = new Vue({
    el: '#app',
    data: {
      table: {
        jogos: [],
        apostas: {}
      }


    },
    computed: {
      selectedGames: function () {
        return this.table["jogos"].filter(a => a['selected'])
      },
      total: function () {
        values = this.selectedGames.map(a => {
          let res = 0
          let apostas = this.table["apostas"][a['id']]
          apostas.forEach(a => res += parseFloat(a['bet']))
          // console.log(res)
          return res;
        })

        let sum = 0;
        values.forEach(a => sum+=a)

        return sum;
      },
      showTotal: function() {
        return this.selectedGames.length > 0;
      }
    }
})


// function () {
//   return this.selectedGames.map(a => {
//     let res = 0
//     let apostas = this.apostas[a['i']]
//     apostas.forEach(a => res += int(a['bet']))
//     console.log(res)
//     return res;
//   })
// }


function updateGameBets(id) {
  $.ajax({ 
    type : "GET", 
    url :  "http://localhost:8080/api/example/jogo/getApostas",
    data: { 
      "id": id,
    },
    //beforeSend: function(xhr){xhr.setRequestHeader('Access-Control-Allow-Headers', 'x-requested-with');},
    success : function(result) { 
        console.log(result);
        result.forEach(a => a['bet'] = 0);
        
        Vue.set(app.table.apostas, id, result)
    }, 
    error : function(result) { 
      //handle the error 
    } 
  }); 
}


$.ajax({ 
  type : "GET", 
  url :  "http://localhost:8080/api/example/jogo/todosJogos",
  //beforeSend: function(xhr){xhr.setRequestHeader('Access-Control-Allow-Headers', 'x-requested-with');},
  success : function(result) { 
      console.log(result);
      result.forEach(a => a['selected']=false)
      Vue.set(app.table, 'jogos', result);
      result.forEach(a => updateGameBets(a['id']))
  }, 
  error : function(result) { 
    //handle the error 
  } 
}); 
