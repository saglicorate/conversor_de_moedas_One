# Conversor de Moedas - Projeto Java

## 📋 Descrição

Este é um projeto completo de conversor de moedas desenvolvido em Java, que simula um ambiente de desenvolvimento do IntelliJ IDEA. O projeto interage com o usuário via console e utiliza a API ExchangeRate-API para obter cotações em tempo real.

## 🏗️ Estrutura do Projeto (IntelliJ IDEA)

```
Desafio - Conversor de Moedas/
├── .idea/                          # Configurações do IntelliJ IDEA
│   ├── compiler.xml               # Configurações do compilador
│   ├── jarRepositories.xml        # Repositórios de dependências
│   ├── misc.xml                   # Configurações gerais
│   ├── modules.xml                # Configurações dos módulos
│   └── workspace.xml              # Configurações do workspace
├── out/                           # Arquivos compilados (.class)
├── src/                           # Código-fonte Java
│   ├── main/java/com/conversor/
│   │   ├── api/                   # Cliente da API
│   │   ├── model/                 # Modelos de dados
│   │   ├── service/               # Serviços de negócio
│   │   └── Main.java             # Classe principal
│   └── test/java/com/conversor/   # Testes unitários
├── postman/                       # Coleção de testes Postman
├── lib/                           # Bibliotecas externas
├── pom.xml                        # Configuração Maven
├── conversor-moedas.iml          # Módulo IntelliJ IDEA
├── .gitignore                     # Arquivos ignorados pelo Git
└── README.md                      # Este arquivo
```

## 🚀 Funcionalidades

### Menu de Opções
O programa oferece um menu com **8 opções de conversão pré-definidas**:

1. **USD → BRL** - Dólar Americano → Real Brasileiro
2. **EUR → BRL** - Euro → Real Brasileiro
3. **BRL → USD** - Real Brasileiro → Dólar Americano
4. **BRL → EUR** - Real Brasileiro → Euro
5. **USD → EUR** - Dólar Americano → Euro
6. **EUR → USD** - Euro → Dólar Americano
7. **GBP → BRL** - Libra Esterlina → Real Brasileiro
8. **JPY → BRL** - Iene Japonês → Real Brasileiro

### Funcionalidades Adicionais
- **Conversão personalizada**: Permite converter entre quaisquer moedas suportadas pela API
- **Histórico de conversões**: Mantém registro de todas as conversões realizadas
- **Estatísticas**: Mostra informações sobre o uso do sistema

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Java 11
- **Build Tool**: Maven
- **API**: ExchangeRate-API 
- **Biblioteca JSON**: Gson 2.10.1
- **Testes**: JUnit 5 + Mockito
- **IDE**: IntelliJ IDEA (configurado)

## 📦 Dependências

### Principais
- **Gson**: Para manipulação de JSON
- **JUnit Jupiter**: Framework de testes
- **Mockito**: Framework de mocking para testes

### Maven
```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>
```

## 🔧 Como Executar

### Pré-requisitos
- Java 11 ou superior
- Maven 3.6 ou superior
- Conexão com internet (para acessar a API)

### Compilação e Execução

#### Via Maven
```bash
# Compilar o projeto
mvn clean compile

# Executar
mvn exec:java -Dexec.mainClass="com.conversor.Main"

# Executar com shade plugin (JAR executável)
mvn clean package
java -jar target/conversor-moedas-1.0.0.jar
```

#### Via IntelliJ IDEA
1. Abra o projeto no IntelliJ IDEA
2. Aguarde o Maven baixar as dependências
3. Execute a classe `Main.java`
4. Use o menu no console para realizar conversões

### Execução Direta
```bash
# Compilar
javac -cp "lib/*" src/main/java/com/conversor/*.java src/main/java/com/conversor/*/*.java

# Executar
java -cp "lib/*:out" com.conversor.Main
```

## 🧪 Testes

### Executar Testes
```bash
# Executar todos os testes
mvn test

# Executar testes específicos
mvn test -Dtest=CurrencyConverterServiceTest
```

### Testes Disponíveis
- **Model Tests**: Testes dos modelos de dados
- **Service Tests**: Testes dos serviços de negócio
- **API Tests**: Testes do cliente da API

## 📡 API ExchangeRate-API

### Endpoint Utilizado
```
GET https://v6.exchangerate-api.com/v6/{API_KEY}/latest/{BASE_CURRENCY}
```

### Exemplo de Resposta
```json
{
  "base_code": "USD",
  "conversion_rates": {
    "BRL": 4.8665,
    "EUR": 0.916,
    "GBP": 0.789,
    "JPY": 149.85
  },
  "time_last_update_utc": "2024-01-15 10:30:00",
  "time_next_update_utc": "2024-01-15 16:30:00"
}
```

## 🧪 Testes Postman

### Coleção Incluída
O projeto inclui uma coleção completa do Postman (`postman/Conversor_Moedas_API.postman_collection.json`) com:

- **Testes de conectividade** com a API
- **Validação de estrutura** das respostas JSON
- **Verificação de status codes**
- **Testes de performance** (tempo de resposta)
- **Validação de dados** retornados

### Como Usar
1. Importe a coleção no Postman
2. Execute os testes para validar a API
3. Verifique os resultados dos testes automatizados

## 📊 Funcionalidades Bônus Implementadas

### ✅ Obrigatório
- [x] Menu com 8+ opções de conversão
- [x] Integração com API ExchangeRate-API
- [x] Uso da biblioteca Gson para JSON
- [x] Testes Postman completos
- [x] Estrutura de projeto IntelliJ IDEA

### ✅ Opcional
- [x] Conversão personalizada entre moedas
- [x] Histórico completo de conversões
- [x] Estatísticas de uso
- [x] Tratamento de erros robusto
- [x] Interface de usuário amigável

## 🔒 Segurança

- **API Key**: Configurada no código (em produção, usar variáveis de ambiente)
- **Validação de entrada**: Verificação de dados do usuário
- **Tratamento de erros**: Captura e tratamento adequado de exceções

## 📝 Logs e Histórico

O sistema mantém um histórico completo de todas as conversões realizadas, incluindo:
- Moedas de origem e destino
- Valores convertidos
- Taxas de câmbio utilizadas
- Timestamps das operações

## 🚨 Tratamento de Erros

- **Erros de conexão**: Tratamento de falhas de rede
- **Erros da API**: Validação de respostas da API
- **Erros de entrada**: Validação de dados do usuário
- **Erros de parsing**: Tratamento de respostas JSON inválidas

## 🔄 Fluxo de Execução

1. **Inicialização**: Carregamento das opções de conversão
2. **Menu**: Exibição das opções disponíveis
3. **Seleção**: Usuário escolhe a conversão desejada
4. **Entrada**: Coleta do valor a ser convertido
5. **API Call**: Conexão com ExchangeRate-API
6. **Processamento**: Cálculo da conversão
7. **Resultado**: Exibição do valor convertido
8. **Histórico**: Armazenamento da operação
9. **Continuidade**: Retorno ao menu principal

## 📈 Melhorias Futuras

- [ ] Interface gráfica (GUI)
- [ ] Persistência em banco de dados
- [ ] Cache de cotações
- [ ] Notificações de mudanças significativas
- [ ] Suporte a criptomoedas
- [ ] Gráficos de evolução das taxas

## 🤝 Contribuição

Para contribuir com o projeto:

1. Faça um fork do repositório
2. Crie uma branch para sua feature
3. Implemente as mudanças
4. Adicione testes
5. Faça commit das mudanças
6. Abra um Pull Request

## 📄 Licença

Este projeto é de uso educacional e para demonstração de habilidades de desenvolvimento Java.

## 👨‍💻 Autor

Desenvolvido como parte de um desafio de programação back-end em Java.

---

**Nota**: Este projeto demonstra boas práticas de desenvolvimento Java, incluindo arquitetura em camadas, tratamento de erros, testes unitários e integração com APIs externas.
