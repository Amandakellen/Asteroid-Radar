
# 🌌 **Asteroid Radar**

Este aplicativo **Android** utiliza a **API da NASA** para exibir uma lista de asteroides próximos à Terra. Ele permite visualizar detalhes individuais de cada asteroide, exibir a imagem do dia da NASA e funciona mesmo **offline** com armazenamento em banco de dados e suporte ao **WorkManager**.

---

## 📱 **Funcionalidades Principais**

### **1. RecyclerView e Navegação**
- **Lista de Asteroides**:  
  - A tela principal exibe uma lista de asteroides usando **RecyclerView**.
  - Ao clicar em um item da lista, o usuário é redirecionado para a **tela de detalhes** do asteroide selecionado.

### **2. Imagem do Dia**
- A **Imagem do Dia** da NASA é exibida no topo da tela principal.
- Carregada dinamicamente da **API da NASA** usando as melhores práticas de **carregamento de imagens**.

### **3. Banco de Dados e Cache Offline**
- **Armazenamento Local**:  
  - Os dados dos asteroides são salvos em um banco de dados local usando **Room**.
  - O aplicativo exibe os asteroides diretamente do banco de dados, mesmo **sem conexão com a internet**.

- **WorkManager**:  
  - O aplicativo realiza as seguintes tarefas **automaticamente uma vez por dia**:
    - Baixa os dados dos próximos **7 dias de asteroides** da API da NASA (requer internet e dispositivo conectado à energia).
    - Remove os dados de asteroides anteriores ao dia atual.

---

## 🌐 **Conexão com a Internet**
- O aplicativo utiliza o **Retrofit** para se conectar à **API da NASA** e obter:
  - Lista de asteroides próximos à Terra.
  - Imagem do dia da NASA.
- Implementa práticas recomendadas de **networking** e **carregamento de imagens**.

---

## 🔎 **Acessibilidade**
- Suporte total ao **TalkBack** para usuários com deficiência visual:
  - Todas as **imagens** e **textos** possuem descrições acessíveis.
    - Exemplo: imagem do asteroide e imagem do dia.
  - O botão de ajuda na tela de **detalhes** possui uma descrição clara.
- Navegação simplificada com botões e feedback sonoro.

---

## 🛠️ **Implementações Técnicas**

### **Arquitetura**
- **MVVM** (Model-View-ViewModel) para garantir separação de responsabilidades.
- Uso do **Repository** como camada de abstração para dados locais e remotos.

### **Tecnologias Utilizadas**
1. **RecyclerView**: Exibição de lista eficiente.
2. **Retrofit**: Conexão com a API da NASA.
3. **Room Database**: Armazenamento local de asteroides.
4. **WorkManager**: Agendamento de tarefas recorrentes.
5. **Glide**: Carregamento de imagens.
6. **Accessibility**: Suporte ao TalkBack e navegação com botões.
7. **Coroutines**: Execução assíncrona para chamadas de rede e banco de dados.

---

## 🎨 **Interface e Estilo**
- A tela de **detalhes** do asteroide apresenta:
  - Subtítulos e valores estilizados para uma apresentação **consistente e elegante**.
- Suporte para:
  - **Diferentes tamanhos de dispositivos**.
  - Orientações **portrait** e **landscape**.

---

## 🧪 **Testes**
### **Testes Unitários**
- Testes em **ViewModels** e **Repositories**.
- Uso de **Coroutines** e **LiveData** para simulação de estados.

---

## 📦 **Requisitos para Compilação**
1. Android Studio Arctic Fox ou superior.
2. Adicionar uma chave da **NASA API** ao `gradle.properties`:
   ```properties
   NASA_API_KEY="sua_chave_aqui"
   ```

---

## 🚀 **Execução**
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/asteroid-radar.git
   ```
2. Abra o projeto no **Android Studio**.
3. Execute o aplicativo em um emulador ou dispositivo físico.

---

## 🌟 **Destaques**
- **Sincronização Automática**:  
  WorkManager gerencia o download e a limpeza de asteroides diariamente.
- **Acessibilidade Completa**:  
  Uso de TalkBack e descrições detalhadas.
- **Offline First**:  
  Banco de dados local garante acesso aos asteroides mesmo sem internet.
- **Experiência Consistente**:  
  Suporte a múltiplos idiomas, tamanhos de tela e orientações.

---

## 📝 **Licença**
Este projeto foi desenvolvido para fins educacionais como parte do curso da Udacity.

---
## 🤝 **Contato**  

Caso tenha dúvidas ou sugestões:  
- **Email:** amandakellensoares@hotmail.com
- **LinkedIn:** [[Seu LinkedIn]](https://www.linkedin.com/in/amanda-kellen/)  

---
